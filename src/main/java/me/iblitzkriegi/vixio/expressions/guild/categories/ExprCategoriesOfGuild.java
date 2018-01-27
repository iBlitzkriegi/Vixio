package me.iblitzkriegi.vixio.expressions.guild.categories;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.List;

public class ExprCategoriesOfGuild extends SimpleExpression<Category> {
    static {
        Vixio.getInstance().registerExpression(ExprCategoriesOfGuild.class, Category.class, ExpressionType.SIMPLE,
                "[the] categories of %guild% [(with|as) %-bot/string%]")
                .setName("Categories of guild")
                .setDesc("Get all of the categories in a guild. They are converted to their names. Must include a bot to modify the categories. Changers: ADD, REMOVE, DELETE")
                .setExample("on guild message received:" +
                        "\tif name of event-bot contains \"Jewel\":\t" +
                        "\t\tset {_cmd::*} to split content of event-message at \" \"" +
                        "\t\tif {_cmd::*} is \"##categories\":" +
                        "\t\t\tset {_m} to a message builder" +
                        "\t\t\tappend \"-= Here are the current categories -=%nl%\" to {_m}" +
                        "\t\t\tappend \"```%nl%\" to {_m}" +
                        "\t\t\tloop categories of event-guild:" +
                        "\t\t\t\tappend \"%name of loop-value% %nl%\" to {_m}" +
                        "\t\t\tappend \"```\" to {_m}");
    }

    private Expression<Guild> guild;
    private Expression<Object> bot;

    @Override
    protected Category[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return null;
        }

        return guild.getCategories().toArray(new Category[guild.getCategories().size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Category> getReturnType() {
        return Category.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "categories of " + guild.toString(e, debug) + (bot == null ? "" : " as " + bot.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("modify categories of guild");
            return;
        }

        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild bindedGuild = Util.bindGuild(bot, this.guild.getSingle(e));
        String name = (String) delta[0];
        if (bot == null || bindedGuild == null) {
            return;
        }

        switch (mode) {
            case ADD:
                try {
                    bindedGuild.getController().createCategory(name).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "create category", x.getPermission().getName());
                }
                break;
            case DELETE:
            case REMOVE:
                try {
                    List<Category> category = bindedGuild.getCategoriesByName(name, false);
                    if (category.size() > 1) {
                        Vixio.getErrorHandler().warn("Vixio attempted to get a Category with the name " + name + " but more than one category exists with that name.");
                        return;
                    }

                    category.get(0).delete().queue();

                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "delete category", x.getPermission().getName());
                }
        }

    }
}

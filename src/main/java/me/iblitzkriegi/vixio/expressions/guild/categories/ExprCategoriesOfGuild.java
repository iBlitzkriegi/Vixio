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
                .setDesc("Get all of the categories in a guild. They are converted to their names.")
                .setExample("Coming Soon");
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
            return;
        }
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        String name = (String) delta[0];
        if (bot == null || guild == null) {
            return;
        }
        switch (mode) {
            case ADD:
                try {
                    if (Util.botIsConnected(bot, guild.getJDA())) {
                        guild.getController().createCategory(name).queue();
                        return;
                    }
                    Guild bindingGuild = Util.bindGuild(bot, guild);
                    if (bindingGuild != null) {
                        bindingGuild.getController().createCategory(name).queue();
                        return;
                    }

                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "create category", x.getPermission().getName());
                }
                break;
            case DELETE:
            case REMOVE:
                try {
                    Guild bindedGuild = Util.bindGuild(bot, guild);
                    if (bindedGuild == null) {
                        return;
                    }
                    List<Category> category = bindedGuild.getCategoriesByName(name, false);
                    if (category.size() > 1) {
                        Vixio.getErrorHandler().warn("Vixio attempted to get a Category with the name " + name + " but more than one category exists with that name.");
                        return;
                    }

                    category.get(0).delete().queue();
                    return;

                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "delete category", x.getPermission().getName());
                }
        }

    }
}

package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprParentOf extends SimpleExpression<Category> {
    static {
        Vixio.getInstance().registerExpression(ExprParentOf.class, Category.class, ExpressionType.SIMPLE,
                "(category|parent) of %channel/channelbuilder% [(with|as) %-bot/string%]")
                .setName("Category of channel")
                .setDesc("Get the Category of a Channel or a Channel you intend to build, aka a Channel Builder. You also must include a bot to set the parent! Changers: SET")
                .setExample("command /channel:" +
                        "\ttrigger:" +
                        "\t\tcreate text channel:" +
                        "\t\t\tset name of the channel to \"Testingxdxd\"" +
                        "\t\t\tset {guild} to guild with id \"219967335266648065\"" +
                        "\t\t\tset nsfw state of the channel as \"Jewel\" to true" +
                        "\t\t\tset topic of the channel as \"Jewel\" to \"Jewel testing\"" +
                        "\t\t\tset parent of the channel to category named \"xd\" in {guild}" +
                        "\t\t\tcreate the channel in {guild} as \"Jewel\"");
    }

    private Expression<Object> channel;
    private Expression<Object> bot;

    @Override
    protected Category[] get(Event e) {
        Object channel = this.channel.getSingle(e);
        if (channel == null) {
            return null;
        }

        if (channel instanceof ChannelBuilder) {
            if (((ChannelBuilder) channel).getParent() != null) {
                return new Category[]{((ChannelBuilder) channel).getParent()};
            }
            return null;
        } else if (channel instanceof Channel) {
            if (((Channel) channel).getParent() != null) {
                return new Category[]{((Channel) channel).getParent()};
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Category> getReturnType() {
        return Category.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "category of " + channel.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channel = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET)
            return new Class[]{Category.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("set parent of channel");
            return;
        }

        Object channel = this.channel.getSingle(e);
        Category category = (Category) delta[0];
        if (channel == null) {
            return;
        }

        if (channel instanceof ChannelBuilder) {
            ((ChannelBuilder) channel).setParent(category);
        } else if (channel instanceof Channel) {
            Bot bot = Util.botFrom(this.bot.getSingle(e));
            Channel bindedChannel = Util.bindChannel(bot, (Channel) channel);
            if (bot == null || bindedChannel == null) {
                return;
            }

            try {
                bindedChannel.getManager().setParent(category).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set category of channel", x.getPermission().getName());
            }
        }
    }
}

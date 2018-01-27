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
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprUserLimit extends SimpleExpression<Integer> {
    static {
        Vixio.getInstance().registerExpression(ExprUserLimit.class, Integer.class, ExpressionType.SIMPLE,
                "[the] user[ ]limit of %voicechannels/channelbuilders% [(with|as) %-bot/string%]")
                .setName("User limit of Voice Channel")
                .setDesc("Get the user limit of a Voice Channel or Channel Builder.")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate voice channel:",
                        "\t\tset name of the channel to \"Testing\"",
                        "\t\tset {guild} to guild with id \"56156156615611\"",
                        "\t\tset user limit of the channel as \"Jewel\" to 69",
                        "\t\tcreate the channel in {guild} as \"Jewel\""
                );
    }

    private Expression<Object> bot;
    private Expression<Object> channels;

    @Override
    protected Integer[] get(Event e) {
        Object[] channels = this.channels.getAll(e);
        if (channels == null) {
            return null;
        }

        List<Integer> userLimits = new ArrayList<>();
        for (Object object : channels) {
            if (object instanceof VoiceChannel) {
                VoiceChannel channel = (VoiceChannel) object;
                userLimits.add(channel.getUserLimit());
            } else if (object instanceof ChannelBuilder) {
                userLimits.add(((ChannelBuilder) object).getUserLimit());
            }
        }

        return userLimits.toArray(new Integer[userLimits.size()]);
    }

    @Override
    public boolean isSingle() {
        return channels.isSingle();
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "user limit of " + channels.toString(e, debug) + (bot != null ? " as " + bot.toString() : "");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channels = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)
            return new Class[]{Number.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("set user limit of channel");
            return;
        }

        Number userLimit = mode == Changer.ChangeMode.SET ? (Number) delta[0] : 0;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Object[] channels = this.channels.getAll(e);
        if (bot == null || channels == null || userLimit == null) {
            return;
        }

        for (Object object : channels) {
            if (object instanceof VoiceChannel) {
                VoiceChannel bindedChannel = Util.bindVoiceChannel(bot, (VoiceChannel) object);
                if (bindedChannel == null) {
                    return;
                }
                try {
                    bindedChannel.getManager().setUserLimit(userLimit.intValue()).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set user limit", x.getPermission().getName());
                } catch (IllegalArgumentException x) {
                    Vixio.getErrorHandler().warn("Vixio attempted to set the userLimit of a channel but the inputted value was not between 0 and 99");
                }
            } else if (object instanceof ChannelBuilder) {
                ((ChannelBuilder) object).setBitRate(userLimit.intValue() * 1000);
            }
        }
    }
}

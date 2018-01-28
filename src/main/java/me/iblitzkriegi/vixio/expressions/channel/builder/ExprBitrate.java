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

public class ExprBitrate extends SimpleExpression<Integer> {
    static {
        Vixio.getInstance().registerExpression(ExprBitrate.class, Integer.class, ExpressionType.SIMPLE,
                "[the] bitrate of %voicechannels/channelbuilders% [(with|as) %-bot/string%]")
                .setName("Bitrate of voice channel")
                .setDesc("Get the bitrate of a voice channel or channel builder. The default value is 64kbps for channel builders. Rates multiplied by 1000. Changers: SET, RESET")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate voice channel:",
                        "\t\tset name of the channel to \"Testing\"",
                        "\t\tset {guild} to guild with id \"56156156615611\"",
                        "\t\tset bitrate of this channel as \"Jewel\" to 69",
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

        List<Integer> bitrates = new ArrayList<>();
        for (Object object : channels) {
            if (object instanceof VoiceChannel) {
                VoiceChannel channel = (VoiceChannel) object;
                bitrates.add(channel.getBitrate() / 1000);
            } else if (object instanceof ChannelBuilder) {
                bitrates.add(((ChannelBuilder) object).getBitRate() / 1000);
            }
        }
        return bitrates.toArray(new Integer[bitrates.size()]);
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
        return "bitrate of " + channels.toString(e, debug) + (bot != null ? " as " + bot.toString() : "");
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
            return;
        }

        Number bitrate = mode == Changer.ChangeMode.SET ? (Number) delta[0] : 64;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Object[] channels = this.channels.getAll(e);
        if (bot == null || channels == null) {
            return;
        }

        for (Object object : channels) {
            if (object instanceof VoiceChannel) {
                VoiceChannel bindedChannel = Util.bindVoiceChannel(bot, (VoiceChannel) object);
                if (bindedChannel != null) {
                    try {
                        bindedChannel.getManager().setBitrate(bitrate.intValue() * 1000).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "set bitrate", x.getPermission().getName());
                    } catch (IllegalArgumentException x) {
                        Vixio.getErrorHandler().warn("Vixio attempted to set the bitrate of a channel but the inputted value was not between 8 and 96.");
                    }
                }
            } else if (object instanceof ChannelBuilder) {
                ((ChannelBuilder) object).setBitRate(bitrate.intValue() * 1000);
            }
        }
    }
}

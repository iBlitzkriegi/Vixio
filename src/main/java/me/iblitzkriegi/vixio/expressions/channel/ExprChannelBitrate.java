package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.bukkit.event.Event;

public class ExprChannelBitrate extends ChangeableSimplePropertyExpression<GuildChannel, Integer> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelBitrate.class, Integer.class,
                "bitrate", "voicechannels/channels")
                .setName("Bitrate of Voice Channel")
                .setDesc("Get the bitrate of a voice channel." +
                        " The default value is 64kbps for channel builders. Rates multiplied by 1000." +
                        " You can set or reset this (resets to 64kbps)")
                .setUserFacing("[the] bitrate[s] of %voicechannels%", "%voicechannels%'[s] bitrate[s]")
                .setExample(
                        "discord command $bitrate <string> <number>:",
                        "\ttrigger:",
                        "\t\tset bitrate of voice channel with id arg-1 to arg-2"
                );
    }

    @Override
    public Integer convert(GuildChannel channel) {
        return channel instanceof VoiceChannel ? ((VoiceChannel) channel).getBitrate() / 1000 : null;
    }

    @Override
    protected String getPropertyName() {
        return "bitrate";
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)
            return new Class[]{Number.class};
        return null;
    }

    @Override
    public boolean shouldError() {
        return false;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (GuildChannel channel : getExpr().getAll(e)) {
            if (channel instanceof VoiceChannel) {
                channel = Util.bindVoiceChannel(bot, (VoiceChannel) channel);
                if (channel != null) {
                    channel.getManager().setBitrate(mode == Changer.ChangeMode.SET ?
                            ((Number) delta[0]).intValue() * 1000 : Util.DEFAULT_BITRATE)
                            .queue();
                }
            }
        }
    }

}
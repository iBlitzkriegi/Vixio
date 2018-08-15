package me.iblitzkriegi.vixio.expressions.channel;


import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprChannelLimit extends ChangeableSimplePropertyExpression<Channel, Integer> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelLimit.class, Integer.class,
                "user limit", "voicechannels/channels")
                .setName("User limit of Voice Channel")
                .setDesc("Get or sets the user limit of a voice channel")
                .setUserFacing("[the] user limit[s] of %voicechannels%", "%voicechannels%'[s] user limit[s]")
                .setExample(
                        "discord command $bitrate <string> <number>:",
                        "\ttrigger:",
                        "\t\tset user limit of voice channel with id arg-1 to arg-2 with event-bot"
                );
    }

    @Override
    public Integer convert(Channel channel) {
        return channel instanceof VoiceChannel ? ((VoiceChannel) channel).getUserLimit() : null;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    protected String getPropertyName() {
        return "user limit";
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Number.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Channel channel : getExpr().getAll(e)) {
            if (channel instanceof VoiceChannel) {
                channel = Util.bindVoiceChannel(bot, (VoiceChannel) channel);
                if (channel != null) {
                    try {
                        channel.getManager().setUserLimit(mode == Changer.ChangeMode.DELETE ? 0 : (((Number) delta[0]).intValue())).queue();
                    } catch (PermissionException ex) {
                        Vixio.getErrorHandler().needsPerm(bot, mode.name().toLowerCase() + " user limit", ex.getPermission().getName());
                    }
                }
            }
        }
    }
}

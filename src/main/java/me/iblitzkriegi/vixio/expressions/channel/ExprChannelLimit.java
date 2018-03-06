package me.iblitzkriegi.vixio.expressions.channel;


import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprChannelLimit extends ChangeableSimplePropertyExpression<VoiceChannel, Integer> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelLimit.class, Integer.class,
                "user limit", "voicechannels")
                .setName("User limit of Voice Channel")
                .setDesc("Get or sets the user limit of a Voice Channel")
                .setExample("reply with \"%user limit of event-voicechannel%\"");
    }

    @Override
    public Integer convert(VoiceChannel channel) {
        return channel.getUserLimit();
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
        for (VoiceChannel channel : getExpr().getAll(e)) {
            channel = Util.bindVoiceChannel(bot, channel);
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

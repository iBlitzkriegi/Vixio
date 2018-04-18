package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprChannelPosition extends ChangeableSimplePropertyExpression<Channel, Number> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelPosition.class, Number.class, "position", "channels")
                .setName("Position of Channel")
                .setDesc("Get the current position of a channel. 0 being the first channel, or the highest position channel.")
                .setExample("set {var} to position of channel with id \"65156156156\"");
    }

    @Override
    protected String getPropertyName() {
        return "position";
    }

    @Override
    public Number convert(Channel channel) {
        return channel.getPosition();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{Number.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        int position = ((Number) delta[0]).intValue();
        for (Channel channel : getExpr().getAll(e)) {
            Channel bindedChannel = Util.bindChannel(bot, channel);
            if (bindedChannel != null) {
                try {
                    bindedChannel.getManager().setPosition(position).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set position of channel", x.getPermission().getName());
                }
            }
        }
    }
}

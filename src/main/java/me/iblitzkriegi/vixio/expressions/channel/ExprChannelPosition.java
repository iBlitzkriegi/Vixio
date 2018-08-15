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
        Vixio.getInstance().registerPropertyExpression(ExprChannelPosition.class, Number.class, "[discord] position", "channels")
                .setName("Position of Channel")
                .setDesc("Get or set the current position of a channel. 0 being the first channel, or the highest position channel.")
                .setExample(
                        "discord command $pos <text>:",
                        "\ttrigger:",
                        "\t\tset {_vc} to channel with id arg-1",
                        "\t\treply with \"%{_vc}%\"",
                        "\t\treply with \"%discord position of {_vc}%\""
                );
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
        return null;
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

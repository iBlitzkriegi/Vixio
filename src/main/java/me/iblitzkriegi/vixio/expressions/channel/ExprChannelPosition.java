package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.managers.RoleManager;
import org.bukkit.event.Event;

public class ExprChannelPosition extends ChangeableSimplePropertyExpression<Object, Number> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelPosition.class, Number.class, "discord position", "channels/roles")
                .setName("Position of")
                .setDesc("Get or set the current position of a role or a channel. With channels, 0 is the highest channel, with roles 0 is the lowest custom role, then 1 is the next role up, then 2...")
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
        return "discord position";
    }

    @Override
    public Number convert(Object object) {
        if (object instanceof Role) {
            return ((Role)object).getPosition();
        } else if (object instanceof Channel) {
            return ((Channel)object).getPosition();
        }
        return null;
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
        for (Object object : getExpr().getAll(e)) {
            if (object instanceof Channel) {
                Channel boundChannel = Util.bindChannel(bot, (Channel) object);
                if (boundChannel != null) {
                    try {
                        boundChannel.getManager().setPosition(position).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "set position of channel", x.getPermission().getName());
                    }
                }
            } else if (object instanceof Role) {
                Role role = ((Role)object);
                try {
                    Guild guild = Util.botIsConnected(bot, role.getJDA()) ? role.getGuild() : Util.bindGuild(bot, role.getGuild());
                    if (guild != null) {
                        guild.getController().modifyRolePositions()
                                .selectPosition(role)
                                .moveTo(position)
                                .queue();
                    }
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set position of role", x.getPermission().getName());
                } catch (IllegalStateException x) {
                    Vixio.getErrorHandler().warn("Vixio attempted to move a role higher than it may go");
                }
            }
        }
    }
}

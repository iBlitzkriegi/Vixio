package me.iblitzkriegi.vixio.expressions.guild.role;

import java.awt.Color;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class ExprRoleColor extends ChangeableSimplePropertyExpression<Role, Color> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprRoleColor.class, Color.class, "colo[u]r", "roles")
                .setName("Color of Role")
                .setDesc("Get the color of a role. This can be set to any color.")
                .setExample("set the color of {_role} to red with event-bot");
    }
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        return new Class[]{Color.class};
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            for (Role role : getExpr().getAll(e)) {
                Guild guild = Util.bindGuild(bot, role.getGuild());
                if (guild != null) {
                    try {
                        guild.getRoleById(role.getId()).getManager().setColor((Color) delta[0]).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "set the color of a role", x.getPermission().getName());
                    }
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "colo[u]r";
    }

    @Override
    public Color convert(Role role) {
        return role.getColor();
    }

    @Override
    public Class<? extends Color> getReturnType() {
        return Color.class;
    }
}

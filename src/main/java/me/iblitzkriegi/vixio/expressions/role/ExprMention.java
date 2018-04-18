package me.iblitzkriegi.vixio.expressions.role;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprMention extends ChangeableSimplePropertyExpression<Role, Boolean> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMention.class, Boolean.class, "mentionable state", "roles")
                .setName("Mentionable State of Role")
                .setDesc("Tell if a role can be publicly mentioned, can be set to true or false")
                .setExample("reply with \"%mentionable state of role with id \"\"55416516516516\"\"%");
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{Boolean.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Role role : getExpr().getAll(e)) {
            Guild guild = Util.bindGuild(bot, role.getGuild());
            if (guild != null) {
                try {
                    role.getManager().setMentionable((Boolean) delta[0]).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set mentionable state of role", x.getPermission().getName());
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "mentionable state";
    }

    @Override
    public Boolean convert(Role role) {
        return role.isMentionable();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}

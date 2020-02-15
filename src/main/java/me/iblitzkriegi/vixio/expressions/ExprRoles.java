package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.member.EvtAddRole;
import me.iblitzkriegi.vixio.events.member.EvtRemoveRole;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

import java.util.List;

public class ExprRoles extends SimpleExpression<Role> {

    static {
        Vixio.getInstance().registerExpression(ExprRoles.class, Role.class, ExpressionType.SIMPLE,
                "[the] role[s]")
                .setName("Added or Removed roles.")
                .setDesc("Get the roles that were added or removed in the member role added and member role removed events.")
                .setExample("SOON");
    }

    private String eventType;

    @Override
    protected Role[] get(Event e) {
        if (eventType.contains("added")) {
            List<Role> roles = ((EvtAddRole.RoleAddEvent) e).getJDAEvent().getRoles();
            return roles.toArray(new Role[roles.size()]);
        }
        List<Role> roles = ((EvtRemoveRole.RoleRemoveEvent) e).getJDAEvent().getRoles();
        return roles.toArray(new Role[roles.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the " + eventType + " roles";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.isCurrentEvent(EvtAddRole.RoleAddEvent.class) || ScriptLoader.isCurrentEvent(EvtRemoveRole.RoleRemoveEvent.class)) {
            eventType = ScriptLoader.isCurrentEvent(EvtAddRole.RoleAddEvent.class) ? "added" : "removed";
            return true;
        }
        Skript.error("This expression may only be used inside either the member role added or member role removed events.");
        return false;
    }
}

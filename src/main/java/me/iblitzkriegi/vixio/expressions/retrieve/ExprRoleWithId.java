package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

import java.util.Set;

public class ExprRoleWithId extends SimpleExpression<Role> {

    static {
        Vixio.getInstance().registerExpression(ExprRoleWithId.class, Role.class, ExpressionType.SIMPLE,
                "role with id %string% [in %-guild%]")
                .setName("Role with ID")
                .setDesc("Get a Role via it's ID. You may get the id of a role by either making the role mentionable and typing @rolename or by looping the roles of the guild and finding it by name. You could also use the `role named` syntax to retrieve the role outright.")
                .setExample("add role with id \"5151561851\" to roles of event-user in event-guild");
    }

    private Expression<String> id;
    private Expression<Guild> guild;

    @Override
    protected Role[] get(Event e) {
        String id = this.id.getSingle(e);
        Guild guild = this.guild == null ? null : this.guild.getSingle(e);
        if (id == null) {
            return null;
        }
        if (guild == null) {
            Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
            for (JDA jda : jdaInstances) {
                try {
                    Role role = jda.getRoleById(id);
                    if (role != null) {
                        return new Role[]{role};
                    }
                } catch (NumberFormatException x) {
                    return null;
                }
            }
            return null;
        }

        try {
            return new Role[]{guild.getRoleById(id)};
        } catch (NumberFormatException x) {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "role with id " + id.toString(e, debug) + (guild == null ? "" : " in " + guild.toString(e, debug));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }

}

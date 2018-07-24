package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

import java.util.List;

public class ExprRoleNamed extends SimpleExpression<Role> {
    static {
        Vixio.getInstance().registerExpression(ExprRoleNamed.class, Role.class, ExpressionType.SIMPLE,
                "role[<s>] named %string% [in %guild%]")
                .setName("Roles Named")
                .setDesc("Get a role via it's name in a Guild! The guild may be assumed in events.")
                .setExample("role[s] named \"Owner\"");
    }

    private Expression<Guild> guild;
    private Expression<String> name;
    private boolean roles;

    @Override
    protected Role[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        String name = this.name.getSingle(e);
        if (guild == null || name == null || name.isEmpty()) {
            return null;
        }

        try {
            List<Role> roles = guild.getRolesByName(name, false);
            if (roles.size() > 1) {
                if (this.roles) {
                    return new Role[]{roles.get(0)};
                }
                return roles.toArray(new Role[roles.size()]);
            }

            return new Role[]{roles.get(0)};
        } catch (NullPointerException | IndexOutOfBoundsException x) {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return roles;
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "role named " + name.toString(e, debug) + " in guild ";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        roles = parseResult.regexes.size() == 0;
        return true;
    }
}

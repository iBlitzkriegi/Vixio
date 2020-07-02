package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

public class ExprRoles extends SimpleExpression<Role> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprRoles.class, Role.class, "role", "guild")
                .setName("Roles of Guild")
                .setDesc("Get all of the roles a guild has.")
                .setExample(
                        "discord command $roles:",
                        "\ttrigger:",
                        "\t\treply with \"Here are the current roles: `%roles of event-guild%`\""
                );
    }

    private Expression<Guild> guild;

    @Override
    protected Role[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return null;
        }

        return guild.getRoles().toArray(new Role[0]);
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
        return "roles of " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        return true;
    }
}

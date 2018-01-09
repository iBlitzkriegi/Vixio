package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

public class ExprRoleWithId extends SimpleExpression<Role> {
    static {
        Vixio.getInstance().registerExpression(ExprRoleWithId.class, Role.class, ExpressionType.SIMPLE,
                "role with id %string% [in %guild%]")
                .setName("Role with ID")
                .setDesc("Get a Role via it's ID, plain and simple.")
                .setExample("Coming Soon!");
    }
    private Expression<String> id;
    private Expression<Guild> guild;
    @Override
    protected Role[] get(Event e) {
        if(guild.getSingle(e) == null){
            Skript.error("You must include a guild to get the role from!");
            return null;
        }
        Guild guild = this.guild.getSingle(e);
        if(id.getSingle(e) == null){
            Skript.error("You must include a ID to find the Role by!");
            return null;
        }
        String id = this.id.getSingle(e);
        if(guild.getRoleById(id) == null){
            Skript.error("Could not find role with that ID in the provided Guild");
            return null;
        }
        try {
            return new Role[]{guild.getRoleById(id)};
        }catch (IllegalArgumentException x){
            Skript.error("You must include a ID to find the Role by!");

        }
        return null;
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
        return "role with id " + id.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(
        name = "RolesInGuild",
        title = "Roles in Guild",
        desc = "Get the Roles in a Guild via its ID",
        syntax = "[discord] roles in guild [with id] %string%",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprRolesInGuild extends SimpleExpression<Role> {
    private Expression<String> vGuild;

    @Override
    protected Role[] get(Event e) {
        return getRoles(e).toArray(new Role[0]);
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
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vGuild = (Expression<String>) expr[0];
        return true;
    }
    private List<Role> getRoles(Event e){
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
           if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){

                return jda.getValue().getGuildById(vGuild.getSingle(e)).getRoles();
           }
        }
        Skript.warning("Could not find a Guild by that ID!");
        return null;
    }
}

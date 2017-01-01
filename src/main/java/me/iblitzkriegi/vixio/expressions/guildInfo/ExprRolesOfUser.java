package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by Blitz on 12/31/2016.
 */
@ExprAnnotation.Expression(returntype = Role.class, type = ExpressionType.SIMPLE, syntax = "role[s] of user %user% in [guild] [with id] %string%")
public class ExprRolesOfUser extends SimpleExpression<Role> {
    Expression<User> vUser;
    Expression<String> vGuild;
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
        vUser = (Expression<User>) expr[0];
        vGuild = (Expression<String>) expr[1];
        return true;
    }
    private List<Role> getRoles(Event e){
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
            if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){
                Guild g = jda.getValue().getGuildById(vGuild.getSingle(e));
                return g.getMember(vUser.getSingle(e)).getRoles();
            }
        }
        return null;
    }
}

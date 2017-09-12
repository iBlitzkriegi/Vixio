package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(
        name = "UsersWithRole",
        title = "Users with Role",
        desc = "Get the Users with a Role via the Role's Name and the Guilds ID",
        syntax = "[discord] users with role [with id] %string%",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprUsersWithRole extends SimpleExpression<User> {
    Expression<String> vRole;

    @Override
    protected User[] get(Event e) {
        return getUsers(e).toArray(new User[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vRole = (Expression<String>) expressions[0];
        return true;
    }
    private List<User> getUsers(Event e) {
        List<User> users = new ArrayList<>();
        for (Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()) {
            if (jda.getValue().getRoleById(vRole.getSingle(e)) != null) {
                Role r = jda.getValue().getRoleById(vRole.getSingle(e));
                Guild g = r.getGuild();
                for (Member m : g.getMembersWithRoles(r)) {
                    users.add(m.getUser());
                }
                break;

            }
        }
        if(users!=null){
            return users;
        }
        Skript.warning("Could not find anything by the provided ID!");
        return null;
    }
}



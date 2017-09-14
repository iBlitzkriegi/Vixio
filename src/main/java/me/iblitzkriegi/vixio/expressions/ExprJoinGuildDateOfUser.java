package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

/**
 * Created by Blitz on 2/11/2017.
 */
@ExprAnnotation.Expression(
        name = "JoinGuildDate",
        title = "Join Guild Date of User",
        desc = "Get the Date in which a User joined a specific Guild, MM/DD/YY",
        syntax = "join guild [with id] %string% date of user %user%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprJoinGuildDateOfUser extends ch.njol.skript.lang.util.SimpleExpression<String>{
    Expression<String> vGuild;
    Expression<User> vUser;
    @Override
    protected String[] get(Event event) {
        return new String[]{getDate(event)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vGuild = (Expression<String>) expressions[0];
        vUser = (Expression<User>) expressions[1];
        return true;
    }
    private String getDate(Event e){
        User u = vUser.getSingle(e);
        Guild g = null;
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
            if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){
                g = jda.getValue().getGuildById(vGuild.getSingle(e));
            }
        }
        String joindate = g.getMember(u).getJoinDate().getMonthValue() + "-" + g.getMember(u).getJoinDate().getDayOfMonth() + "-" + g.getMember(u).getJoinDate().getYear();
        return joindate;
    }
}

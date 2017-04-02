package me.iblitzkriegi.vixio.expressions.loopables;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by Blitz on 2/7/2017.
 */
@ExprAnnotation.Expression(
        name = "MembersInGuild",
        title = "Users in Guild",
        desc = "Get the Users in a Guild, loopable",
        syntax = "members in guild [with id] %string%",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprMembersInGuild extends SimpleExpression<Member>{
    Expression<String> vGuild;
    @Override
    protected Member[] get(Event e) {
        return getMembers(e).toArray(new Member[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vGuild = (Expression<String>) expressions[0];
        return true;
    }
    private List<Member> getMembers(Event e){
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
            if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){
                return jda.getValue().getGuildById(vGuild.getSingle(e)).getMembers();
            }
        }
        Skript.warning("Could not find guild with that ID!");
        return null;
    }
}

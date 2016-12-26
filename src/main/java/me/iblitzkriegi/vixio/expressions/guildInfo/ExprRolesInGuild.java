package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(returntype = List.class, type = ExpressionType.SIMPLE, syntax = "[discord] roles in guild %string%")
public class ExprRolesInGuild extends SimpleExpression<List> {
    Expression<String> vGuild;
    @Override
    protected List[] get(Event e) {
        return new List[]{getRoles(e)};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends List> getReturnType() {
        return List.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vGuild = (Expression<String>) e[0];
        return true;
    }
    private List getRoles(Event e){
        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){
                return jda.getValue().getGuildById(vGuild.getSingle(e)).getRoles();
            }
        }
        return null;
    }
}

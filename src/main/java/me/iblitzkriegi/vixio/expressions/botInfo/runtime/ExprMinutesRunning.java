package me.iblitzkriegi.vixio.expressions.botInfo.runtime;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

import java.util.Date;

/**
 * Created by Blitz on 11/2/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "(min|minutes) running for (bot|user) %string%")
public class ExprMinutesRunning extends SimpleExpression<String> {
    private Expression<String> vBotName;
    @Override
    protected String[] get(Event e) {
        return new String[]{getRuntime(e)};
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
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBotName = (Expression<String>) expr[0];
        return true;
    }
    private String getRuntime(Event e){
        if(EffLogin.botruntime.get(vBotName.getSingle(e))!=null){
            long seconds = getTimeRunning(e) / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            return String.valueOf(minutes);
        }else{
            return null;
        }
    }
    private long getTimeRunning(Event e){
        Date date = new Date();
        return (date.getTime() - EffLogin.botruntime.get(vBotName.getSingle(e)));
    }
}

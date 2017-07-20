package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 4/5/2017.
 */
@ExprAnnotation.Expression(
        name = "SystemTime",
        title = "System Time",
        desc = "Get the System time, can be used to make a ping command!",
        syntax = "(time of system|system time)",
        returntype = Number.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprSystemtime extends ch.njol.skript.lang.util.SimpleExpression<Number> {
    @Override
    protected Number[] get(Event event) {
        return new Number[]{System.currentTimeMillis()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

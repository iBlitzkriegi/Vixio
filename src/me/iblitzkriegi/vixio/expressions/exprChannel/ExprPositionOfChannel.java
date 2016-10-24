package me.iblitzkriegi.vixio.expressions.exprChannel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/20/2016.
 */
public class ExprPositionOfChannel extends SimpleExpression<String> {
    Expression<String> id;

    @Override
    protected String[] get(Event e) {
        String s = String.valueOf(getAPI().getJDA().getTextChannelById(id.getSingle(e)).getPosition());
        if (s != null) {
            return new String[]{s};
        } else {
            return new String[]{"Could not find user."};

        }
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
        id = (Expression<String>) expr[0];
        return true;
    }
}

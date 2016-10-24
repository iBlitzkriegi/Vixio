package me.iblitzkriegi.vixio.expressions.exprUserValues;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/20/2016.
 */
public class ExprUserIsBot extends SimpleExpression<Boolean> {
    Expression<Boolean> user;

    @Override
    protected Boolean[] get(Event e) {
        Boolean s = getAPI().getJDA().getUserById(String.valueOf(user.getSingle(e))).isBot();
        if (s != null) {
            return new Boolean[]{s};
        } else {
            Skript.warning("Could not find user.");
            return null;
        }

    }
    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        user = (Expression<Boolean>) expr[0];
        return true;
    }
}

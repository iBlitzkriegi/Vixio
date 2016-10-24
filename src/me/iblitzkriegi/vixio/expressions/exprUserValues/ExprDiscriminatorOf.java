package me.iblitzkriegi.vixio.expressions.exprUserValues;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/20/2016.
 */
public class ExprDiscriminatorOf extends SimpleExpression<String> {
    Expression<String> user;

    @Override
    protected String[] get(Event e) {
        String s = getAPI().getJDA().getUserById(user.getSingle(e)).getDiscriminator();
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
        user = (Expression<String>) expr[0];
        return true;
    }
}

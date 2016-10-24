package me.iblitzkriegi.vixio.expressions.exprMentioned;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/21/2016.
 */
public class ExprMentioned extends SimpleExpression<String>{
    @Override
    protected String[] get(Event e) {
        return new String[]{getMentioned(e)};
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
        return true;
    }
    private static String getMentioned(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMsgReceived) {
            return ((EvntGuildMsgReceived) e).getMentioned().getId();
        }
        return null;
    }
}

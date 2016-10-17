package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/15/2016.
 */
public class ExprAuthorAsMention extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getAuthorAsMention(e)};
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
        if (ScriptLoader.isCurrentEvent(EvntGuildMsgReceived.class)) {
            return true;
        }else if (ScriptLoader.isCurrentEvent(EvntPrivateMessageReceived.class)) {
            return true;
        }
        Skript.warning("Cannot use 'AuthorAsMention' outside of discord events!");
        return false;
    }
    private static String getAuthorAsMention(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMsgReceived) {
            return ((EvntGuildMsgReceived) e).getEvtAuthor().getAsMention();
        }else if(e instanceof EvntPrivateMessageReceived){
            return ((EvntPrivateMessageReceived)e).getAsMention();
        }
        return null;
    }

}


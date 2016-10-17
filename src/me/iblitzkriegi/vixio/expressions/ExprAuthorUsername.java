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
 * Created by Blitz on 10/16/2016.
 */
public class ExprAuthorUsername extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getUsername(e)};
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
        }else if(ScriptLoader.isCurrentEvent(EvntPrivateMessageReceived.class)) {
            return true;
        }
        Skript.warning("Cannot use 'Author-Username' outside of discord events!");
        return false;
    }
    public static String getUsername(Event e){
        if(e == null){
            return null;
        }else if(e instanceof EvntGuildMsgReceived){
            return ((EvntGuildMsgReceived)e).getEvtAuthor().getUsername();
        }else if(e instanceof EvntPrivateMessageReceived){
            return ((EvntPrivateMessageReceived)e).getEvntAuthor().getUsername();
        }
        return null;
    }
}

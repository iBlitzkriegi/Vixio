package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/15/2016.
 */
public class ExprMessage extends SimpleExpression<String> {

    @Override
    protected String[] get(Event e) {
        return new String[]{getMessage(e)};
    }

    @Nullable
    private static String getMessage(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMsgReceived) {
            return ((EvntGuildMsgReceived) e).getEvtMsg();
        }else if(e instanceof EvntPrivateMessageReceived){
            return ((EvntPrivateMessageReceived) e).getEvntMsg();
        }
        return null;
    }

    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
        if (ScriptLoader.isCurrentEvent(EvntGuildMsgReceived.class)) {
            return true;
        }else if(ScriptLoader.isCurrentEvent(EvntPrivateMessageReceived.class)) {
            return true;
        }
        Skript.warning("Cannot use 'Message' outside of discord events!");
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return getClass().getName();
    }
}
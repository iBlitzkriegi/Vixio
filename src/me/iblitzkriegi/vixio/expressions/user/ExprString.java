package me.iblitzkriegi.vixio.expressions.user;

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
 * Created by Blitz on 10/17/2016.
 */
public class ExprString extends SimpleExpression<String>{
    @Override
    protected String[] get(Event e) {
        return new String[]{getString(e)};
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
        Skript.warning("Cannot use 'Message' outside of discord events!");
        return false;
    }
    @Nullable
    private static String getString(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMsgReceived) {
            return ((EvntGuildMsgReceived) e).getEvtMessageAsString();
        }else if(e instanceof EvntPrivateMessageReceived){
            return ((EvntPrivateMessageReceived) e).getEvntMsgAsString();
        }
        return null;
    }
}

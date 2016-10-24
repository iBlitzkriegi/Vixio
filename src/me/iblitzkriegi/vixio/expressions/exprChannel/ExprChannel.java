package me.iblitzkriegi.vixio.expressions.exprChannel;


import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import net.dv8tion.jda.entities.PrivateChannel;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/15/2016.
 */
public class ExprChannel extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getChannel(e)};
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
        Skript.warning("Cannot use 'Channel' outside of discord events!");
        return false;
    }
    private static String getChannel(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMsgReceived) {
            return ((EvntGuildMsgReceived) e).getEvtChannel();
        }else if(e instanceof EvntPrivateMessageReceived){
            getPmChannel(e);
        }
        return null;

    }
    private static PrivateChannel getPmChannel(Event e){
        return ((EvntPrivateMessageReceived)e).getEvntAuthor().getPrivateChannel();
    }

}

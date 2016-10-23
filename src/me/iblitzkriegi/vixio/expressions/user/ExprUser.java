package me.iblitzkriegi.vixio.expressions.user;


import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/15/2016.
 */
public class ExprUser extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getAuthorAsId(e)};
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
        }else if(ScriptLoader.isCurrentEvent(EvntPrivateMessageReceived.class)){
            return true;
        }else if(ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)){
            return true;
        }else if(ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)){
            return true;
        }
        Skript.warning("Cannot use 'event-user' outside of discord events!");
        return false;
    }
    private static String getAuthorAsId(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMsgReceived) {
            return ((EvntGuildMsgReceived) e).getEvtAuthor().getId();
        }else if(e instanceof EvntPrivateMessageReceived){
            return ((EvntPrivateMessageReceived)e).getEvntAuthor().getId();
        }else if(e instanceof EvntGuildMemberLeave){
            return ((EvntGuildMemberLeave)e).getEvntUser().getId();
        }else if(e instanceof EvntGuildMemberJoin){
            return ((EvntGuildMemberJoin)e).getEvntUser();
        }
        return null;
    }
}

package me.iblitzkriegi.vixio.expressions.exprGuild;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/22/2016.
 */
public class ExprGuild extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getGuild(e)};
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
        }else if(ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)){
            return true;
        }else if(ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)){
            return true;
        }
        Skript.warning("Cannot use 'event-guild' outside of discord events!");
        return false;
    }
    public String getGuild(Event e){
        if(e == null){
            return null;
        }else if(e instanceof EvntGuildMsgReceived){
            return ((EvntGuildMsgReceived)e).getEvntGuild().getId();
        }else if(e instanceof EvntGuildMemberJoin){
            return ((EvntGuildMemberJoin)e).getEvntGuild().getId();
        }else if(e instanceof EvntGuildMemberLeave){
            return ((EvntGuildMemberLeave)e).getEvntGuild().getId();
        }
        return "Could not find guild.";
    }
}

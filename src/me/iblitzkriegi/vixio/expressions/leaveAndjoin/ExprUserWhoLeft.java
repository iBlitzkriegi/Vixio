package me.iblitzkriegi.vixio.expressions.leaveAndjoin;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/21/2016.
 */
public class ExprUserWhoLeft extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getUser(e)};
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
        if (ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)) {
            return true;
        }
        Skript.warning("Cannot use 'event-user' outside of discord events!");
        return false;
    }
    private String getUser(Event e){
        if(e==null){
            return null;
        }else if(e instanceof EvntGuildMemberLeave) {
            return ((EvntGuildMemberLeave) e).getEvntUser().getId();
        }
        return null;

    }
}

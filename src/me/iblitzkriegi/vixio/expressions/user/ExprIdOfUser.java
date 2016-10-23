package me.iblitzkriegi.vixio.expressions.user;


import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/15/2016.
 */
public class ExprIdOfUser extends SimpleExpression<String> {
    private Expression<String> user;
    @Override
    protected String[] get(Event e) {
        String s = getAPI().getJDA().getUserById(user.getSingle(e)).getId();
        return new String[]{s};
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
        if (ScriptLoader.isCurrentEvent(EvntGuildMsgReceived.class)) {
            user = (SimpleExpression<String>) expr[0];
            return true;
        }else if(ScriptLoader.isCurrentEvent(EvntPrivateMessageReceived.class)){
            user = (SimpleExpression<String>) expr[0];
            return true;
        }
        Skript.warning("Cannot use 'AuthorAsId' outside of discord events!");
        return false;
    }
}

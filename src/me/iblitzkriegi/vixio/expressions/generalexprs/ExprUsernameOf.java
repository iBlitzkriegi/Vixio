package me.iblitzkriegi.vixio.expressions.generalexprs;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/20/2016.
 */
public class ExprUsernameOf extends SimpleExpression<String> {
    Expression<String> user;

    @Override
    protected String[] get(Event e) {
        if(e instanceof EvntGuildMemberJoin){
            return new String[]{((EvntGuildMemberJoin)e).getEvntUserObj().getUsername()};
        }else if(e instanceof EvntGuildMemberLeave){
            return new String[]{((EvntGuildMemberLeave)e).getEvntUser().getUsername()};
        }
        String s = getAPI().getJDA().getUserById(user.getSingle(e)).getUsername();
        if (s != null) {
            return new String[]{s};
        } else {
            return new String[]{"Could not find user."};

        }
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
            user = (Expression<String>) expr[0];
            return true;
        }else if (ScriptLoader.isCurrentEvent(EvntPrivateMessageReceived.class)) {
            user = (Expression<String>) expr[0];
            return true;
        }else if (ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)) {
            user = (Expression<String>) expr[0];
            return true;
        }else if(ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)){
            return  true;
        }
        return false;
    }
}

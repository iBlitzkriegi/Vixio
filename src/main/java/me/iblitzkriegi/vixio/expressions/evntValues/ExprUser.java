package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.*;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(returntype = User.class, type = ExpressionType.SIMPLE, syntax = "[event-]user")
public class ExprUser extends SimpleExpression<User>{
    @Override
    protected User[] get(Event e) {
        return new User[]{getUser(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class) | ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)| ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)|ScriptLoader.isCurrentEvent(EvntPrivateMessageReceive.class) | ScriptLoader.isCurrentEvent(EvntUserStatusChange.class)){
            return true;
        }
        Skript.warning("Cannot use 'event-user' outside of discord events!");
        return false;
    }
    private static User getUser(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getEvntUser();
        }else if(e instanceof EvntPrivateMessageReceive){
            return ((EvntPrivateMessageReceive)e).getEvntUser();
        }else if(e instanceof EvntGuildMemberJoin){
            return ((EvntGuildMemberJoin)e).getEvntUser();
        }else if (e instanceof EvntGuildMemberLeave) {
            return ((EvntGuildMemberLeave) e).getEvntUser();
        }else if (e instanceof EvntUserStatusChange) {
            return ((EvntUserStatusChange) e).getEvntUser();
        }
        return null;
    }

}

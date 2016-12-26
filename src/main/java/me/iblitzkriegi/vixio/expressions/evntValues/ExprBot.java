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
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(returntype = User.class, type = ExpressionType.SIMPLE, syntax = "[event-]bot")
public class ExprBot extends SimpleExpression<User> {
    @Override
    protected User[] get(Event e) {
        return new User[]{getBot(e)};
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
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)
                | ScriptLoader.isCurrentEvent(EvntPrivateMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntUserStatusChange.class)
                ){
            return true;
        }
        Skript.warning("You may not use event-bot outside of Discord events.");
        return false;
    }
    @Nullable
    private static User getBot(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getJDA().getSelfUser();
        }else if (e instanceof EvntPrivateMessageReceive) {
            return ((EvntPrivateMessageReceive) e).getJDA().getSelfUser();
        }else if (e instanceof EvntGuildMemberJoin) {
            return ((EvntGuildMemberJoin) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntGuildMemberLeave) {
            return ((EvntGuildMemberLeave) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntUserStatusChange) {
            return ((EvntUserStatusChange) e).getEvntJDA().getSelfUser();
        }
        return null;
    }

}

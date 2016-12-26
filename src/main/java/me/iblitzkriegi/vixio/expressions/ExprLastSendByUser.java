package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.jdaEvents.GuildMessageReceived.getLast;

/**
 * Created by Blitz on 12/22/2016.
 */
@ExprAnnotation.Expression(returntype = Message.class, type = ExpressionType.SIMPLE, syntax = "last message sent by user %user%")
public class ExprLastSendByUser extends SimpleExpression<Message> {
    Expression<User> vUser;
    @Override
    protected Message[] get(Event e) {
        return new Message[]{getMessage(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
    private Message getMessage(Event e){
        Message message = getLast.get(vUser.getSingle(e));
        if(message!=null){
            return getLast.get(vUser.getSingle(e));
        }else{
            Skript.warning("That user has not sent any messages while I've been online");
        }
        return null;
    }
}

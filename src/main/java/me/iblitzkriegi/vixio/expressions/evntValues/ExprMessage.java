package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceive;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(returntype = Message.class, type = ExpressionType.SIMPLE, syntax = "[event-]message")
public class ExprMessage extends SimpleExpression<Message> {
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)){
            return true;
        }
        Skript.warning("You may not use event-message outside of Discord events.");
        return false;
    }
    @Nullable
    private static Message getMessage(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getEvntMessage();
        }else if(e instanceof EvntPrivateMessageReceive){
            return ((EvntPrivateMessageReceive)e).getEvntMessage();
        }
        return null;
    }
}

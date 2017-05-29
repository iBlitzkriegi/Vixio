package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.*;
import me.iblitzkriegi.vixio.jdaEvents.PrivateMessageReceived;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(
        name = "eventchannel",
        title = "event-bot",
        desc = "Get the channel out of a PM event",
        syntax = "[event-]channel",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprChannel extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getBot(e)};
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
        if(ScriptLoader.isCurrentEvent(EvntPrivateMessageSend.class)){
            return true;
        }
        Skript.warning("You may not use event-bot outside of Discord events.");
        return false;
    }
    @Nullable
    private static String getBot(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntPrivateMessageSend) {
            return ((EvntPrivateMessageSend) e).getEvntChannel().getId();
        }else if (e instanceof EvntPrivateMessageReceive) {
            return ((EvntPrivateMessageReceive) e).getEvntChannel().getId();
        }
        return null;
    }

}

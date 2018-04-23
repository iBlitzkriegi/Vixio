package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

import java.util.List;

public class ExprChannelPinned extends SimpleExpression<Message> {
    static {
        Vixio.getInstance().registerExpression(ExprChannelPinned.class, Message.class, ExpressionType.SIMPLE,
                "[last] (grabbed|retrieved) (pins|pinned messages)")
                .setName("Last Retrieved Pins")
                .setDesc("Get the results of the last \"grab pinned messages\" search.")
                .setExample(
                        "grab pinned messages in event-channel",
                        "reply with \"%grabbed pins%\""
                );
    }

    public static List<Message> pinnedMessages;

    @Override
    protected Message[] get(Event e) {
        if (pinnedMessages == null || pinnedMessages.isEmpty()) {
            return null;
        }
        return pinnedMessages.toArray(new Message[pinnedMessages.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last retrieved pinned messages";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

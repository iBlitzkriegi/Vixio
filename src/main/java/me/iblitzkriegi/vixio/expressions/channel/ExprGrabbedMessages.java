package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.channel.EffGrabMessages;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import org.bukkit.event.Event;

import java.util.List;

public class ExprGrabbedMessages extends SimpleExpression<UpdatingMessage> {
    static {
        Vixio.getInstance().registerExpression(ExprGrabbedMessages.class, UpdatingMessage.class, ExpressionType.SIMPLE,
                "[the] grabbed messages")
                .setName("Grabbed Messages")
                .setDesc("Get the grabbed messages from the grab messages effect. The messages can be deleted, which purges them.")
                .setExample("delete the grabbed messages with event-bot");
    }

    @Override
    protected UpdatingMessage[] get(Event e) {
        List<UpdatingMessage> messages = EffGrabMessages.updatingMessages;
        if (messages == null) {
            return null;
        }
        EffGrabMessages.updatingMessages = null;
        return messages.toArray(new UpdatingMessage[messages.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends UpdatingMessage> getReturnType() {
        return UpdatingMessage.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grabbed messages";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

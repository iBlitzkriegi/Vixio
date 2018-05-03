package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/19/2017.
 */
public class ExprLastRetrievedMessage extends SimpleExpression<Message> {

    public static Message lastRetrievedMessage;

    static {
        Vixio.getInstance().registerExpression(ExprLastRetrievedMessage.class, Message.class, ExpressionType.SIMPLE,
                "last retrieved [discord] message")
                .setName("Last retrieved Message")
                .setDesc("Get the last retrieved message called from the retrieve message effect. Cleared every time the retrieve message effect is used.")
                .setExample("set {_message} to last retrieved message");
    }

    @Override
    protected Message[] get(Event event) {
        return new Message[]{lastRetrievedMessage};
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
        return "last retrieved message";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

}

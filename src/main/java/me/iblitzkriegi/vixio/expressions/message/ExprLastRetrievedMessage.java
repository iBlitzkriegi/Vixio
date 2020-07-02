package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/19/2017.
 */
public class ExprLastRetrievedMessage extends SimpleExpression<UpdatingMessage> {

    public static UpdatingMessage lastRetrievedMessage;

    static {
        Vixio.getInstance().registerExpression(ExprLastRetrievedMessage.class, UpdatingMessage.class, ExpressionType.SIMPLE,
                "last retrieved [discord] message")
                .setName("Last retrieved Message")
                .setDesc("Get the last retrieved message called from the retrieve message effect. Cleared every time the retrieve message effect is used.")
                .setExample("set {_message} to last retrieved message");
    }

    @Override
    protected UpdatingMessage[] get(Event event) {
        return new UpdatingMessage[]{lastRetrievedMessage};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends UpdatingMessage> getReturnType() {
        return UpdatingMessage.class;
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

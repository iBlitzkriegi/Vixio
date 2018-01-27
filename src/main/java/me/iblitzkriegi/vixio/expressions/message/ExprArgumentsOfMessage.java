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
 * Created by Blitz on 7/26/2017.
 */
public class ExprArgumentsOfMessage extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprArgumentsOfMessage.class, String.class, ExpressionType.SIMPLE,
                "(%message%[']s arguments|[the] vixio arguments of %message%)")
                .setName("Arguments of message")
                .setDesc("Get the arguments of a Message split up for you")
                .setExample("set {_var::*} to arguments of event-message #where {_var::1} is now the command");
    }

    private Expression<Message> message;

    @Override
    protected String[] get(Event e) {
        Message message = this.message.getSingle(e);
        if (message == null) {
            return null;
        }

        return message.getContentDisplay().split(" ");
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return message.toString(event, b) + "'s arguments";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expressions[0];
        return true;
    }
}

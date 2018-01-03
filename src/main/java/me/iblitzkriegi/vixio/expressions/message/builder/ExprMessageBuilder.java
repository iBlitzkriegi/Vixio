package me.iblitzkriegi.vixio.expressions.message.builder;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import org.bukkit.event.Event;

public class ExprMessageBuilder extends SimpleExpression<MessageBuilder>{
    static {
        Vixio.getInstance().registerExpression(ExprMessageBuilder.class, MessageBuilder.class, ExpressionType.SIMPLE , "a [new] message builder")
                .setName("Message Builder")
                .setDesc("Create a new MessageBuilder")
                .setExample("set {e} to a new message builder");
    }
    @Override
    protected MessageBuilder[] get(Event e) {
        return new MessageBuilder[]{new MessageBuilder()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MessageBuilder> getReturnType() {
        return MessageBuilder.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "a new message builder";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

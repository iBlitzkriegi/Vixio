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
                .setDesc("Get the grabbed messages from the grab messages effect. This can be used in the purge effect to purge the messages.")
                .setExample(
                        "discord command $purge <number>:",
                        "\texecutable in: guild",
                        "\ttrigger:",
                        "\t\tset {_num} to arg-1 ",
                        "\t\tgrab the last {_num} messages in event-channel",
                        "\t\tpurge the grabbed messages with event-bot",
                        "\t\tset {_error} to last vixio error ",
                        "\t\tif {_error} is set:",
                        "\t\t\treply with \"I ran into an error! `%{_error}%`\"",
                        "\t\t\tstop",
                        "\t\treply with \"I have successfully purged %arg-1% messages\""
                );
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

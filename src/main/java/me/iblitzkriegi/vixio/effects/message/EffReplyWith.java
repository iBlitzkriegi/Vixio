package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.registrations.EventValues;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.util.Arrays;

/**
 * Created by Blitz on 7/27/2017.
 */
public class EffReplyWith extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffReplyWith.class, "reply with %messages/strings% [and store (it|the message) in %-objects%]")
                .setName("Reply with")
                .setDesc("Reply with a message in a event")
                .setExample("reply with \"Hello %mention tag of event-user%\"");

    }

    private Expression<Object> message;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        MessageChannel channel = EventValues.getEventValue(e, MessageChannel.class, 0);
        Bot bot = EventValues.getEventValue(e, Bot.class, 0);
        if (channel == null || bot == null) {
            return;
        }
        Object[] objects = message.getAll(e);
        if (objects == null) {
            return;
        }
        try {
            channel = Util.bindChannel(bot, channel);
            for (Object s : objects) {
                Message message = Util.messageFrom(s);
                if (message != null) {
                    if (varExpr == null) {
                        channel.sendMessage(message).queue();
                    } else {
                        try {
                            Message resultingMessage = channel.sendMessage(message).complete(true);
                            if (resultingMessage != null) {
                                Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                            }
                        } catch (RateLimitedException e1) {
                            Vixio.getErrorHandler().warn("Vixio tried to reply with and store a message but was rate limited");
                        }
                    }
                }
            }
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "send message");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "reply with " + message.toString(event, b);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.getCurrentEvents() != null && Arrays.stream(ScriptLoader.getCurrentEvents())
                .anyMatch(event -> EventValues.getEventValueGetter(event, MessageChannel.class, 0) != null) && Arrays.stream(ScriptLoader.getCurrentEvents())
                .anyMatch(event -> EventValues.getEventValueGetter(event, Bot.class, 0) != null)) {
            message = (Expression<Object>) exprs[0];
            if (exprs[1] instanceof Variable) {
                varExpr = (Variable<?>) exprs[1];
                varName = SkriptUtil.getVariableName(varExpr);
            }

            return true;
        }

        Skript.error("You can't use reply with in events that do not have a channel and bot to reply with.");
        return false;
    }
}

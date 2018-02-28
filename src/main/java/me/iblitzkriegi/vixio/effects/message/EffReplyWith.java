package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;

/**
 * Created by Blitz on 7/27/2017.
 */
public class EffReplyWith extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffReplyWith.class, "reply with %messages/strings%")
                .setName("Reply with")
                .setDesc("Reply with a message in a event")
                .setUserFacing("reply with \"%messages%\"")
                .setExample("reply with \"Hello %mention tag of event-user%\"");

    }

    private Expression<Object> message;

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
            //TODO: this needs to have the bot binded (maybe, it should work out fine because in most cases the event values line up)
            for (Object s : objects) {
                Message message = Util.messageFrom(s);
                if (message != null) {
                    channel.sendMessage(message).queue();
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.getCurrentEvents() != null && Arrays.stream(ScriptLoader.getCurrentEvents())
                .anyMatch(event -> EventValues.getEventValueGetter(event, MessageChannel.class, 0) != null) && Arrays.stream(ScriptLoader.getCurrentEvents())
                .anyMatch(event -> EventValues.getEventValueGetter(event, Bot.class, 0) != null)) {
            message = (Expression<Object>) expressions[0];
            return true;
        }
        Skript.error("You can't use reply with in events that do not have a channel and bot to reply with.");
        return false;
    }
}

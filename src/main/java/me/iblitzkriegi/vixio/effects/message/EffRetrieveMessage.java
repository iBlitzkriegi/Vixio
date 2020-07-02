package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.message.ExprLastRetrievedMessage;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffRetrieveMessage extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffRetrieveMessage.class, "retrieve message [with id] %string% [(in|from) %channel/user%]")
                .setName("Retrieve message with id")
                .setDesc("Get a Message via it's ID from a Guild/TextChannel")
                .setExample(
                        "discord command $addReaction <text> <text>:",
                        "\ttrigger:",
                        "\t\tretrieve message with id arg-1 ",
                        "\t\tif last retrieved message is not set:",
                        "\t\t\treply with \"Could not find a message with that id!\"",
                        "\t\t\tstop",
                        "\t\tadd reaction arg-2 to reactions of last retrieved message with event-bot"
                );
    }

    private Expression<String> id;
    private Expression<Object> channel;

    @Override
    protected void execute(Event e) {
        ExprLastRetrievedMessage.lastRetrievedMessage = null;

        String id = this.id.getSingle(e);
        Object input = this.channel.getSingle(e);

        if (id == null || id.isEmpty() || input == null) {
            return;
        }
        if (!(input instanceof MessageChannel || input instanceof User)) {
            return;
        }

        MessageChannel messageChannel = null;
        try {
            messageChannel = input instanceof MessageChannel ? (MessageChannel) input : ((User) input).openPrivateChannel().complete(true);
        } catch (RateLimitedException x) {
            Vixio.getErrorHandler().warn("Vixio attempted to open a private channel but was ratelimited.");
        }

        if (messageChannel != null) {
            try {
                Message message = messageChannel.retrieveMessageById(id).complete(true);
                ExprLastRetrievedMessage.lastRetrievedMessage = UpdatingMessage.from(message);
            } catch (RateLimitedException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve a message but was ratelimited.");
            } catch (NumberFormatException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve a message with a ID but the input was not a number");
            } catch (ErrorResponseException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve a message with ID but the message was unknown.");
            }
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return "retrieve message with id " + id.toString(event, b) + " from " + channel.toString(event, b);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        channel = (Expression<Object>) expressions[1];
        return true;
    }

}

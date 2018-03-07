package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.message.ExprLastRetrievedMessage;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffRetrieveMessage extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffRetrieveMessage.class, "retrieve message [with id] %string% [(in|from) %channel%]")
                .setName("Retrieve message with id")
                .setDesc("Get a Message via it's ID from a Guild/TextChannel")
                .setExample("retrieve message with id \"1265152161551661561\" from channel event-channel");
    }

    private Expression<String> id;
    private Expression<TextChannel> channel;

    @Override
    protected void execute(Event e) {
        //TODO: support dms
        ExprLastRetrievedMessage.lastRetrievedMessage = null;
        String id = this.id.getSingle(e);
        Channel channel = this.channel.getSingle(e);

        if (id == null || id.isEmpty() || channel == null) {
            return;
        }
        if (!channel.getType().equals(ChannelType.TEXT)) {
            return;
        }

        TextChannel textChannel = (TextChannel) channel;
        try {
            ExprLastRetrievedMessage.lastRetrievedMessage = textChannel.getMessageById(id).complete(true);
        } catch (RateLimitedException | ErrorResponseException e1) {
            e1.printStackTrace();
            Vixio.getErrorHandler().warn("Vixio tried to retrieve a message but was rate limited");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "retrieve message with id " + id.toString(event, b) + " from " + channel.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        channel = (Expression<TextChannel>) expressions[1];
        return true;
    }

}

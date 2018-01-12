package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.message.ExprLastRetrievedMessage;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/19/2017.
 */
public class EffRetrieveMessage extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffRetrieveMessage.class, "retrieve message with id %string% [from %channel%]")
            .setName("Retrieve message with id")
            .setDesc("Get a Message via it's ID from a Guild/TextChannel")
            .setExample("retrieve message with id \"1265152161551661561\" from channel event-channel");
    }
    Expression<String> id;
    Expression<TextChannel> channel;
    @Override
    protected void execute(Event e) {
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return;
        }
        Channel channel = this.channel.getSingle(e);

        if (channel == null) {
            return;
        }
        if (!channel.getType().equals(ChannelType.TEXT)) {
            return;
        }
        TextChannel textChannel = (TextChannel) channel;
        textChannel.getMessageById(id).queue(message -> ExprLastRetrievedMessage.lastRetrievedMessage = message);
        return;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "retrieve message with id " + id.toString(event, b) + " from channel " + channel.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        channel = (Expression<TextChannel>) expressions[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.message.ExprLastRetrievedMessage;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/19/2017.
 */
public class EffRetrieveMessage extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffRetrieveMessage.class, "retrieve message with id %string% from [text[-]]channel %channel%")
            .setName("Retrieve message with id")
            .setDesc("Get a Message via it's ID from a Guild/TextChannel")
            .setExample("retrieve message with id \"1265152161551661561\" from channel event-channel");
    }
    Expression<String> id;
    Expression<MessageChannel> channel;
    @Override
    protected void execute(Event event) {
        if(id.getSingle(event)!=null) {
            if (channel.getSingle(event) != null) {
                channel.getSingle(event).getMessageById(id.getSingle(event)).queue(message -> ExprLastRetrievedMessage.lastRetrievedMessage = message);
            } else {
                Skript.error("You must include a TextChannel in order to retrieve the Message from it");
            }
        }else{
            Skript.error("You must include a id in order to retrieve a Message");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "retrieve message with id " + id.getSingle(event) + " from channel " + channel.getSingle(event);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        channel = (Expression<MessageChannel>) expressions[1];
        return true;
    }
}

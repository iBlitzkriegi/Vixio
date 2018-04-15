package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.message.ExprLastRetrievedMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffRetrieveMessage extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffRetrieveMessage.class, "retrieve message [with id] %string% [(in|from) %channel/user%] [and store (it|the message) in %-objects%]")
                .setName("Retrieve message with id")
                .setDesc("Get a Message via it's ID from a Guild/TextChannel")
                .setExample("retrieve message with id \"1265152161551661561\" from channel event-channel");
    }

    private Expression<String> id;
    private Expression<Object> channel;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        ExprLastRetrievedMessage.lastRetrievedMessage = null;
        String id = this.id.getSingle(e);
        Object input = this.channel.getSingle(e);

        if (id == null || id.isEmpty() || input == null) {
            return;
        }
        if (!(input instanceof Channel || input instanceof User)) {
            return;
        }
        MessageChannel messageChannel = null;
        try {
            messageChannel = input instanceof Channel ? (MessageChannel) input : ((User) input).openPrivateChannel().complete(true);
        } catch (RateLimitedException x) {
            Vixio.getErrorHandler().warn("Vixio attempted to open a private channel but was ratelimited.");
        }
        if (messageChannel.getType() == ChannelType.VOICE){
            return;
        }
        if (varExpr == null) {
            messageChannel.getMessageById(id).queue(message -> ExprLastRetrievedMessage.lastRetrievedMessage = message);
        } else {
            try {
                Message message = messageChannel.getMessageById(id).complete(true);
                ExprLastRetrievedMessage.lastRetrievedMessage = message;
                Util.storeInVar(varName, varExpr, message, e);
            } catch (RateLimitedException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve a message but was ratelimited.");
            } catch (NumberFormatException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve a message with a ID but the input was not a number");
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "retrieve message with id " + id.toString(event, b) + " from " + channel.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        channel = (Expression<Object>) expressions[1];
        if (expressions[2] instanceof Variable) {
            varExpr = (Variable<?>) expressions[2];
            varName = SkriptUtil.getVariableName(varExpr);
        }
        return true;
    }

}

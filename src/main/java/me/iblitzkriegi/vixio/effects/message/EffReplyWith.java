package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.EvntMessageReceived;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/27/2017.
 */
public class EffReplyWith extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffReplyWith.class, "reply with %strings/messages%")
            .setName("Reply with")
            .setDesc("Reply with a message in a event")
            .setUserFacing("reply with \"%messages%\"")
            .setExample("reply with \"Hello %mention tag of event-user%\"");

    }

    private Expression<Object> message;
    @Override
    protected void execute(Event e) {
        if (message != null) {
            if(e instanceof EvntMessageReceived){
                TextChannel channel = (TextChannel) ((EvntMessageReceived) e).getChannel();
                try {
                    for (Object s : message.getAll(e)) {
                        channel.sendMessage(Util.messageFrom(s)).queue();

                    }
                }catch (PermissionException x){
                    Skript.error("Bot does not have permission to send messages in channel, needed permission: MESSAGE_WRITE");
                }
            }
        }else{
            Skript.error("You must provide a %string% to be sent!");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "reply with " + message.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntMessageReceived.class)) {
            message = (Expression<Object>) expressions[0];
            return true;
        }
        Skript.error("You may not use `reply with` in events that do not have a text channel to reply in.");
        return false;
    }
}

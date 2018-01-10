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
import me.iblitzkriegi.vixio.util.enums.VixioError;
import net.dv8tion.jda.core.entities.Message;
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
        if (e instanceof EvntMessageReceived){
            Object[] objects = message.getAll(e);
            if (objects == null){
                Skript.error("You must provide a %string% or a %message% to be sent!");
                return;
            }
            TextChannel channel = (TextChannel) ((EvntMessageReceived) e).getChannel();
            try{
                for(Object s : objects){
                    channel.sendMessage(Util.messageFrom(s)).queue();
                }

            }catch (PermissionException x){
                Vixio.getErrorHandler().warn(VixioError.BOT_NO_PERMISSION, ((EvntMessageReceived) e).getBot(), x.getPermission().getName(), "send message");
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "reply with " + message.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.isCurrentEvent(EvntMessageReceived.class)) {
            message = (Expression<Object>) expressions[0];
            return true;
        }
        Skript.error("You may not use `reply with` in events that do not have a text channel to reply in.");
        return false;
    }
}

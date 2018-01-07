package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffSendMessage extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffSendMessage.class, "penis %messages% to %textchannels% [with %-bot%]")
                .setName("Send Message to Text Channel")
                .setDesc("Send a Message to a Text Channel.")
                .setExample("COMING BACK 2 DIS")
                .setUserFacing("send %message/string/messagebuilder/embedbuilder% to %textchannels% [with %bot%]");
    }
    private Expression<Message> message;
    private Expression<MessageChannel> channel;
    private Expression<Bot> bot;

    @Override
    protected void execute(Event e) {
        try {
            Bot bot = this.bot.getSingle(e);
            if (bot != null) {
                if (bot.getJDA() != null) {
                    for (MessageChannel channel : channel.getAll(e)) {
                        for (Message msg : message.getAll(e)) {
                            bot.getJDA().getTextChannelById(channel.getId()).sendMessage(msg).queue();
                        }
                    }
                }
            }
            }catch(PermissionException x){
                Skript.error("The requested bot does not have permissions to send messages in one of the requested channels.");
            }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + message.toString(e, debug) + " to " + channel.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) exprs[0];
        channel = (Expression<MessageChannel>) exprs[1];
        bot = (Expression<Bot>) exprs[2];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffSendMessage extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffSendMessage.class, "send %messages/strings% to %channels% with %bot/string%")
                .setName("Send Message to Text Channel")
                .setDesc("Send a Message to a Text Channel.")
                .setExample("COMING BACK 2 DIS")
                .setUserFacing("send %message/string/messagebuilder/embedbuilder% to %channels% with %bot/string%");
    }
    private Expression<Object> message;
    private Expression<Channel> channel;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        try {
            if(this.bot.getSingle(e)!=null) {
                Bot bot = Util.botFrom(this.bot.getSingle(e));
                if (bot != null) {
                    if (bot.getJDA() != null) {
                        for (Channel channel : channel.getAll(e)) {
                            if (channel.getType().equals(ChannelType.TEXT)) {
                                for (Object m : message.getAll(e)) {
                                    bot.getJDA().getTextChannelById(channel.getId()).sendMessage(Util.messageFrom(m)).queue();
                                }
                            } else {
                                Skript.error("The inputted channel is not a text channel! I can't send things to voice channels!");
                            }
                        }
                    }
                }
            }else{
                Skript.error("You must include either a %bot% or the name you gave it it with the login effect!");
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
        message = (Expression<Object>) exprs[0];
        channel = (Expression<Channel>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }
}

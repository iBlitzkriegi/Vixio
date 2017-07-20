package me.iblitzkriegi.vixio.events.message;

import me.iblitzkriegi.vixio.registration.annotation.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/30/2016.
 */
@EvntAnnotation.Event(name = "PrivateMessageSent", title = "Private Message Sent", desc = "Fired when the bot sends a private message",  type = MultiBotGuildCompare.class,
        syntax = "[discord ]private message sent by %string%", example = "TESTING STUFF")
public class EvntPrivateMessageSend extends org.bukkit.event.Event{
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User sUser;
    private PrivateChannel sChannel;
    private Message sMsg;
    private JDA sJDA;

    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public EvntPrivateMessageSend(PrivateChannel channel, Message msg, User author, JDA jda) {
        sUser = author;
        sChannel = channel;
        sMsg = msg;
        sJDA = jda;
    }
    public User getEvntUser(){
        return sUser;
    }
    public Message getEvntMessage(){
        return sMsg;
    }
    public PrivateChannel getEvntChannel(){
        return sChannel;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public JDA getJDA(){
        return sJDA;
    }


}

package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/30/2016.
 */
@EvntAnnotation.Event(name = "PrivateMessageReceived", type = MultiBotGuildCompare.class, syntax = "[discord ]private message sent to %string%")
public class EvntPrivateMessageReceive extends org.bukkit.event.Event{
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User sUser;
    private PrivateChannel sChannel;
    private Message sMsg;
    private User sBot;
    private JDA sJDA;

    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public EvntPrivateMessageReceive(User author, PrivateChannel channel, Message msg, User bot, JDA jda) {
        sUser = author;
        sChannel = channel;
        sMsg = msg;
        sBot = bot;
        sJDA = jda;
    }
    public User getEvntUser(){
        return sUser;
    }
    public User getBot(){
        return sBot;
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
    public void setCancelled(boolean c) {
        cancel = c;
    }
    public JDA getJDA(){
        return sJDA;
    }


}

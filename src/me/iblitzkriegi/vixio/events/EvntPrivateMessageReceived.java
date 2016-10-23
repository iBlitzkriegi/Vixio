package me.iblitzkriegi.vixio.events;

import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/16/2016.
 */
public class EvntPrivateMessageReceived extends org.bukkit.event.Event implements Cancellable {
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User Author;
    private PrivateChannel Channel;
    private Message message;
    public EvntPrivateMessageReceived(User author, PrivateChannel channel, Message msg){
        cancel = false;
        Author = author;
        Channel = channel;
        message = msg;
    }
    public User getEvntAuthor(){
        return Author;
    }
    public String getAsMention(){
        return Author.getAsMention();
    }
    public String getEvntMsg(){return message.getId();}
    public String getEvntMsgAsString(){return message.getContent();}
    @Override
    public boolean isCancelled() {
        return false;
    }
    public String getEvntChannel(){
        return Channel.getId();
    }
    @Override
    public void setCancelled(boolean b) {
        cancel = b;
    }

    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList(){return hls;}
}

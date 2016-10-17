package me.iblitzkriegi.vixio.events;

import net.dv8tion.jda.entities.Channel;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/15/2016.
 */
public class EvntGuildMsgReceived extends org.bukkit.event.Event implements Cancellable {
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User sAuthor;
    private Channel sChannel;
    private String sMsg;

    public EvntGuildMsgReceived(User author, Channel channel, String msg) {
        cancel = false;
        sAuthor = author;
        sChannel = channel;
        sMsg = msg;
    }

    public User getEvtAuthor() {
        return sAuthor;
    }

    public String getEvtChannel() {
        return sChannel.getId();
    }
    public String getEvntChannelId(){return sChannel.getId();}

    public String getEvtMsg() {
        return sMsg;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean c) {
        cancel = c;
    }

    @Override
    public HandlerList getHandlers() {
        return hls;
    }

    public static HandlerList getHandlerList() {
        return hls;
    }
}

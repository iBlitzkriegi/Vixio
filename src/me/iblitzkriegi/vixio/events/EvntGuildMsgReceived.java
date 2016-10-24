package me.iblitzkriegi.vixio.events;

import net.dv8tion.jda.entities.*;
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
    private Message sMsg;
    private User sMentioned;
    private Guild sGuild;

    public EvntGuildMsgReceived(User author, Channel channel, Message msg, User mentioned, Guild guild) {
        cancel = false;
        sAuthor = author;
        sChannel = channel;
        sMsg = msg;
        sMentioned = mentioned;
        sGuild = guild;
    }

    public User getEvtAuthor() {
        return sAuthor;
    }
    public User getMentioned() {
        if(sMentioned==null){
            return null;
        }else {
            return sMentioned;
        }
    }
    public String getEvtMessageAsString(){return sMsg.getContent();}
    public Guild getEvntGuild(){return sGuild;}

    public String getEvtChannel() {
        return sChannel.getId();
    }
    public String getEvntChannelId(){return sChannel.getId();}

    public String getEvtMsg() {
        return sMsg.getId();
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean c) {
        cancel = c;
    }
    public Message getMsgObject(){
        return sMsg;
    }
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public Channel getEvntChannelOb(){
        return sChannel;
    }

    public static HandlerList getHandlerList() {
        return hls;
    }
}

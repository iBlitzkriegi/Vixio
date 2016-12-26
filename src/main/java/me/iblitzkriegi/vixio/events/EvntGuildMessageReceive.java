package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/30/2016.
 */
@EvntAnnotation.Event(name = "GuildMessageReceived", type = MultiBotGuildCompare.class, syntax = "[discord ]guild message receive[d] seen by %string%")
public class EvntGuildMessageReceive extends org.bukkit.event.Event{
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User sAuthor;
    private Channel sChannel;
    private Message sMsg;
    private User sMentioned;
    private Guild sGuild;
    private User sBot;
    private JDA sJDA;



    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public EvntGuildMessageReceive(User author, Channel channel, Message msg, User mentioned, Guild guild, User bot, JDA jda) {
        sAuthor = author;
        sChannel = channel;
        sMsg = msg;
        sMentioned = mentioned;
        sGuild = guild;
        sBot = bot;
        sJDA = jda;
    }
    public User getEvntUser(){
        return sAuthor;
    }
    public User getMentioned() {
        if (sMentioned == null) {
            return null;
        } else {
            return sMentioned;
        }
    }
    public User getBot(){
        return sBot;
    }
    public Guild getGuild(){return sGuild;}
    public Message getEvntMessage(){
        return sMsg;
    }
    public Channel getEvntChannel(){
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

package me.iblitzkriegi.vixio.events.message;

import me.iblitzkriegi.vixio.registration.annotation.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/30/2016.
 */
@EvntAnnotation.Event(
        name = "GuildBotMessageSend",
        title = "Guild Bot Message Send",
        desc = "Fired when the bot sends a message",
        type = MultiBotGuildCompare.class,
        syntax = "[discord ]guild message sent by %string%",
        example = "on guild message sent by \\\"Rawr\\\":\\n" +
                "\\tbroadcast \\\"Bot sent a msg to %event-channel%\\\"")
public class EvntGuildMessageBotSend extends Event{
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User sAuthor;
    private Channel sChannel;
    private Message sMsg;
    private Guild sGuild;
    private User sBot;
    private JDA sJDA;
    private TextChannel vChnl;



    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public EvntGuildMessageBotSend(User author, Channel channel, Message msg, Guild guild, User bot, JDA jda, TextChannel chnl) {
        sAuthor = author;
        sChannel = channel;
        sMsg = msg;
        sGuild = guild;
        sBot = bot;
        sJDA = jda;
        vChnl = chnl;
    }
    public User getEvntUser(){
        return sAuthor;
    }
    public TextChannel getEvntTextChannel(){
        return vChnl;
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

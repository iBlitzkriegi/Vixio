package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by Blitz on 10/30/2016.
 */
@EvntAnnotation.Event(
        name = "GuildMessageReceived",
        title = "Guild Message Receive",
        desc = "Fired when a user sends a message in a Guild",
        type = MultiBotGuildCompare.class,
        syntax = "[discord ]guild message receive[d] seen by %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$ping\\\":\\n" +
                "\\t\\treply with \\\"Pong!\\\"")
public class EvntGuildMessageReceive extends Event{
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    private User sAuthor;
    private Channel sChannel;
    private Message sMsg;
    private Guild sGuild;
    private User sBot;
    private JDA sJDA;
    private List vMentioned;


    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public EvntGuildMessageReceive(User author, Channel channel, Message msg, List mentioned, Guild guild, User bot, JDA jda) {
        sAuthor = author;
        sChannel = channel;
        sMsg = msg;
        sGuild = guild;
        vMentioned = mentioned;
        sBot = bot;
        sJDA = jda;
    }
    public User getEvntUser(){
        return sAuthor;
    }
    public List getMention(){
        return vMentioned;
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
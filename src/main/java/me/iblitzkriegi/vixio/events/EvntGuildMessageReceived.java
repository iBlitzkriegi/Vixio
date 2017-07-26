package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleEvent;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class EvntGuildMessageReceived extends Event{
    static {
        Vixio.registerEvent("GuildMessageRec", SimpleEvent.class, EvntGuildMessageReceived.class, "guild message receive[d]")
                .setName("GuildMessageRec")
                .setDesc("Event")
                .setExample("on guild message receive:");
    }
    private static final HandlerList hls = new HandlerList();
    private User vAuthor;
    private TextChannel vChannel;
    private Message vMsg;
    private List<?> vMentioned;
    private Guild vGuild;
    private User vBot;
    private JDA vJDA;
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public EvntGuildMessageReceived(User author, TextChannel channel, Message msg, List<?> mentioned, Guild guild, User bot, JDA jda){
        vAuthor = author;
        vChannel = channel;
        vMsg = msg;
        vMentioned = mentioned;
        vGuild = guild;
        vBot = bot;
        vJDA = jda;
    }
    public User author(){
        return vAuthor;
    }
    public  TextChannel getChannel(){
        return vChannel;
    }
    public Message getMessage(){
        return vMsg;
    }
    public List<?> getMentioned(){
        return vMentioned;
    }
    public Guild getGuild(){
        return vGuild;
    }
    public User getBot(){
        return vBot;
    }
    public JDA getJDA(){
        return vJDA;
    }



}

package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class EventGuildMessageReceived extends Event{
    static {
        Vixio.registerEvent("GuildMessageRec", SimpleEvent.class, EventGuildMessageReceived.class, "guild message receive[d]")
                .setName("GuildMessageRec")
                .setDesc("Event")
                .setExample("on guild message receive:");
        EventValues.registerEventValue(EventGuildMessageReceived.class, User.class, new Getter<User, EventGuildMessageReceived>() {
            @Override
            public User get(EventGuildMessageReceived evntGuildMessageReceive) {
                return evntGuildMessageReceive.author();
            }
        },0);
        EventValues.registerEventValue(EventGuildMessageReceived.class, Guild.class, new Getter<Guild, EventGuildMessageReceived>() {
            @Override
            public Guild get(EventGuildMessageReceived evntGuildMessageReceive) {
                return evntGuildMessageReceive.getGuild();
            }
        },0);
        EventValues.registerEventValue(EventGuildMessageReceived.class, Channel.class, new Getter<Channel, EventGuildMessageReceived>() {
            @Override
            public Channel get(EventGuildMessageReceived evntGuildMessageReceive) {
                return evntGuildMessageReceive.getChannel();
            }
        },0);
        EventValues.registerEventValue(EventGuildMessageReceived.class, Message.class, new Getter<Message, EventGuildMessageReceived>() {
            @Override
            public Message get(EventGuildMessageReceived evntGuildMessageReceive) {
                return evntGuildMessageReceive.getMessage();
            }
        },0);
        EventValues.registerEventValue(EventGuildMessageReceived.class, String.class, new Getter<String, EventGuildMessageReceived>() {
            @Override
            public String get(EventGuildMessageReceived evntGuildMessageReceive) {
                return evntGuildMessageReceive.getMessage().getContent();
            }
        },0);
    }
    private static final HandlerList hls = new HandlerList();
    private User author;
    private TextChannel channel;
    private Message message;
    private List<?> mentionedUsers;
    private Guild guild;
    private SelfUser bot;
    private JDA jda;
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public EventGuildMessageReceived(User author, TextChannel channel, Message message, List<?> mentioned, Guild guild, SelfUser bot, JDA jda){
        this.author = author;
        this.channel = channel;
        this.message = message;
        this.mentionedUsers = mentioned;
        this.guild = guild;
        this.bot = bot;
        this.jda = jda;
    }
    public User author(){
        return author;
    }
    public TextChannel getChannel(){
        return channel;
    }
    public Message getMessage(){
        return message;
    }
    public List<?> getMentioned(){
        return mentionedUsers;
    }
    public Guild getGuild(){
        return guild;
    }
    public SelfUser getBot(){
        return bot;
    }
    public JDA getJDA(){
        return jda;
    }



}

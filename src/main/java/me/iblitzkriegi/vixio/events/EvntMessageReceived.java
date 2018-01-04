package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvntMessageReceived extends Event{
    static {
        Vixio.getInstance().registerEvent("GuildMessageReceived", SimpleEvent.class, EvntMessageReceived.class, "(guild|server) message [received]")
                .setName("Guild Message Received")
                .setDesc("Fired when a message is sent in a Text Channel that the bot can read.")
                .setExample("on server message received");
        EventValues.registerEventValue(EvntMessageReceived.class, Channel.class, new Getter<Channel, EvntMessageReceived>() {
            @Override
            public Channel get(EvntMessageReceived event) {
                return event.getChannel();
            }},0);
        EventValues.registerEventValue(EvntMessageReceived.class, User.class, new Getter<User, EvntMessageReceived>() {
            @Override
            public User get(EvntMessageReceived event) {
                return event.getUser();
            }},0);
        EventValues.registerEventValue(EvntMessageReceived.class, Member.class, new Getter<Member, EvntMessageReceived>() {
            @Override
            public Member get(EvntMessageReceived event) {
                return event.getMember();
            }},0);
        EventValues.registerEventValue(EvntMessageReceived.class, Message.class, new Getter<Message, EvntMessageReceived>() {
            @Override
            public Message get(EvntMessageReceived event) {
                return event.getMessage();
            }},0);
        EventValues.registerEventValue(EvntMessageReceived.class, Guild.class, new Getter<Guild, EvntMessageReceived>() {
            @Override
            public Guild get(EvntMessageReceived event) {
                return event.getGuild();
            }},0);
        EventValues.registerEventValue(EvntMessageReceived.class, Bot.class, new Getter<Bot, EvntMessageReceived>() {
            @Override
            public Bot get(EvntMessageReceived event) {
                return event.getBot();
            }},0);
    }
    private User user;
    private Guild guild;
    private Member member;
    private Message message;
    private TextChannel channel;
    private JDA jda;
    private Bot bot;
    private static final HandlerList hls = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public EvntMessageReceived(Member member, TextChannel channel, Message message, JDA jda){
        this.user = member.getUser();
        this.guild = channel.getGuild();
        this.member = member;
        this.message = message;
        this.channel = channel;
        this.jda = jda;
        Bot bot = Vixio.getInstance().botHashMap.get(jda);
        if(bot!=null){
            this.bot = bot;
        }else{
            this.bot = null;
        }

    }
    public User getUser(){
        return user;
    }
    public Guild getGuild(){
        return guild;
    }
    public Member getMember(){
        return member;
    }
    public Message getMessage(){
        return message;
    }
    public JDA getJDA() {
        return jda;
    }
    public Bot getBot() {
        return bot;
    }
    public TextChannel getChannel() {
        return channel;
    }
}

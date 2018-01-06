package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvntMessageReceived extends Event{
    static {
        Vixio.getInstance().registerEvent("GuildMessageReceived", SimpleEvent.class, EvntMessageReceived.class, "(guild|server) message [received]")
                .setName("Guild Message Received")
                .setDesc("Fired when a message is sent in a Text Channel that the bot can read.")
                .setExample("on server message");
        EventValues.registerEventValue(EvntMessageReceived.class, TextChannel.class, new Getter<TextChannel, EvntMessageReceived>() {
            @Override
            public TextChannel get(EvntMessageReceived event) {
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
    }
    private User user;
    private Guild guild;
    private Member member;
    private Message message;
    private TextChannel channel;
    private JDA jda;
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

    public TextChannel getChannel() {
        return channel;
    }
}

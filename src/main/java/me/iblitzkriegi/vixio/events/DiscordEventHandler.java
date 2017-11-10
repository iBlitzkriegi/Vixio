package me.iblitzkriegi.vixio.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Blitz on 8/9/2017.
 */

/**
 * Created by Blitz on 7/22/2017.
 */
public class DiscordEventHandler extends Event{
    private static HashMap<String, Object> eventValues = new HashMap<>();
    private static ArrayList<String> values = new ArrayList<>();
    static {
        values.add("User");values.add("TextChannel");values.add("Member"); values.add("Guild");values.add("VoiceChannel");values.add("Message");values.add("JDA");values.add("String");
        EventValues.registerEventValue(DiscordEventHandler.class, TextChannel.class, new Getter<TextChannel, DiscordEventHandler>() {
            @Override
            public TextChannel get(DiscordEventHandler event) {
                if (event.getObject("TextChannel") != null) {
                    if (event.getObject("TextChannel") instanceof TextChannel) {
                        TextChannel m = (TextChannel) event.getObject("TextChannel");
                        return m;
                    }
                }
                return null;
            }},0);
        EventValues.registerEventValue(DiscordEventHandler.class, User.class, new Getter<User, DiscordEventHandler>() {
            @Override
            public User get(DiscordEventHandler event) {
                if (event.getObject("User") != null) {
                    if (event.getObject("User") instanceof User) {
                        User m = (User) event.getObject("User");
                        return m;
                    }
                }
                return null;
            }},0);
        EventValues.registerEventValue(DiscordEventHandler.class, Guild.class, new Getter<Guild, DiscordEventHandler>() {
            @Override
            public Guild get(DiscordEventHandler event) {
                if (event.getObject("Guild") != null) {
                    if (event.getObject("Guild") instanceof Guild) {
                        Guild m = (Guild) event.getObject("Guild");
                        return m;
                    }
                }
                return null;
            }},0);
        EventValues.registerEventValue(DiscordEventHandler.class, VoiceChannel.class, new Getter<VoiceChannel, DiscordEventHandler>() {
            @Override
            public VoiceChannel get(DiscordEventHandler event) {
                if (event.getObject("VoiceChannel") != null) {
                    if (event.getObject("VoiceChannel") instanceof VoiceChannel) {
                        VoiceChannel m = (VoiceChannel) event.getObject("VoiceChannel");
                        return m;
                    }
                }
                return null;
            }},0);
        EventValues.registerEventValue(DiscordEventHandler.class, Message.class, new Getter<Message, DiscordEventHandler>() {
            @Override
            public Message get(DiscordEventHandler event) {
                if (event.getObject("Message") != null) {
                    if (event.getObject("Message") instanceof Message) {
                        Message m = (Message) event.getObject("Message");
                        return m;
                    }
                }
                return null;
            }},0);
        EventValues.registerEventValue(DiscordEventHandler.class, Member.class, new Getter<Member, DiscordEventHandler>() {
            @Override
            public Member get(DiscordEventHandler event) {
                if (event.getObject("Member") != null) {
                    if (event.getObject("Member") instanceof Member) {
                        Member m = (Member) event.getObject("Member");
                        return m;
                    }
                }
                return null;
            }},0);
        EventValues.registerEventValue(DiscordEventHandler.class, String.class, new Getter<String, DiscordEventHandler>() {
            @Override
            public String get(DiscordEventHandler event) {
                if (event.getObject("String") != null) {
                    if (event.getObject("String") instanceof String) {
                        String m = (String) event.getObject("String");
                        return m;
                    }
                }
                return null;
            }},0);




    }

    private static final HandlerList hls = new HandlerList();
    public static net.dv8tion.jda.core.events.Event event;
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public DiscordEventHandler(net.dv8tion.jda.core.events.Event e, Object... objects) {
        event = e;
        ArrayList<String> noNull = new ArrayList<>();
        for(Object object : objects){
            String obj = object.getClass().getSimpleName().replaceFirst("Impl", "");
            if(values.contains(obj)) {
                noNull.add(obj);
                if (object instanceof User) {
                    eventValues.put("User", object);
                } else if (object instanceof TextChannel) {
                    eventValues.put("TextChannel", object);
                } else if (object instanceof Guild) {
                    eventValues.put("Guild", object);
                } else if (object instanceof VoiceChannel) {
                    eventValues.put("VoiceChannel", object);
                } else if (object instanceof Message) {
                    eventValues.put("Message", object);
                } else if (object instanceof Member) {
                    eventValues.put("Member", object);
                }else if(object instanceof JDA){
                    eventValues.put("SelfUser", ((JDA) object).getSelfUser());
                }else if(object instanceof String){
                    eventValues.put("String", object);
                }
            }
        }
        for(Object obj : eventValues.keySet()){
            if(!noNull.contains(obj)){
                eventValues.put(obj.getClass().getSimpleName().replaceFirst("Impl", ""), null);
            }
        }
        noNull.clear();

    }
    public static net.dv8tion.jda.core.events.Event getEvent(){
        return event;
    }
    public static Object getObject(String s){
        if(eventValues.get(s)!=null){
            return eventValues.get(s);
        }
        Skript.error("There's no types." + s.toLowerCase() + " in a " + event.getClass().getSimpleName().replaceAll("(?<!^)(?=[A-Z])", " ").toLowerCase());
        return null;
    }
    public static net.dv8tion.jda.core.events.Event getJdaEvent(){
        return event;
    }


}


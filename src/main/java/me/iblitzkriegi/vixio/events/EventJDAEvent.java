package me.iblitzkriegi.vixio.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.MultiEventCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Blitz on 7/28/2017.
 */
public class EventJDAEvent extends org.bukkit.event.Event {
    static {
        Vixio.registerEvent("JDAGenericEvent", MultiEventCompare.class, EventJDAEvent.class, "discord event [%string%]")
                .setName("OnDiscordEvent")
                .setDesc("Generic discord event")
                .setExample("none");
        EventValues.registerEventValue(EventJDAEvent.class, Message.class, new Getter<Message, EventJDAEvent>() {
            @Override
            public Message get(EventJDAEvent event) {if (event.getObject("Message") != null) {if(event.getObject("Message") instanceof Message){Message m = (Message) event.getObject("Message");return m;}}Skript.error("Event value is not present in requested event.");return null;}},0);
        EventValues.registerEventValue(EventJDAEvent.class, User.class, new Getter<User, EventJDAEvent>() {
            @Override
            public User get(EventJDAEvent event) {if (event.getObject("User") != null) {if(event.getObject("User") instanceof User){User m = (User) event.getObject("User");return m;}}Skript.error("Event value is not present in requested event.");return null;}},0);
        EventValues.registerEventValue(EventJDAEvent.class, Channel.class, new Getter<Channel, EventJDAEvent>() {
            @Override
            public Channel get(EventJDAEvent event) {
                if (event.getObject("Channel") != null) {
                    if (event.getObject("Channel") instanceof Channel) {
                        Channel m = (Channel) event.getObject("Channel");
                        return m;
                    }
                }
                Skript.error("Event value is not present in requested event.");
                return null;
            }},0);
        EventValues.registerEventValue(EventJDAEvent.class, Guild.class, new Getter<Guild, EventJDAEvent>() {
            @Override
            public Guild get(EventJDAEvent event) {if (event.getObject("Guild") != null) {if(event.getObject("Guild") instanceof Guild){Guild m = (Guild) event.getObject("Guild");return m;}}Skript.error("Event value is not present in requested event.");return null;}},0);

    }
    private static final HandlerList h1s = new HandlerList();
    private Event event;
    private Object[] objects;
    private HashMap<String, Object> values = new HashMap<>();
    @Override
    public HandlerList getHandlers() {
        return h1s;
    }
    public static HandlerList getHandlerList() {
        return h1s;
    }
    public EventJDAEvent(Event e, Object... oj){
         event = e;
         objects = oj;
         for(Object s : oj){
             if(s instanceof User){
                 User user = (User) s;
                 values.put("User", user);
             }else if(s instanceof Guild){
                 Guild guild = (Guild) s;
                 values.put("Guild", guild);
             }else if(s instanceof Channel){
                 Channel c = (Channel) s;
                 values.put("Channel", c);
             }else if(s instanceof JDA){
                 JDA jda = (JDA) s;
                 values.put("JDA", jda);
             }
         }
    }
    public Object getObject(String s){
        if(values.get(s)!=null){
            return values.get(s);
        }
        return null;
    }
    public Event getEvent(){
        return event;
    }


}

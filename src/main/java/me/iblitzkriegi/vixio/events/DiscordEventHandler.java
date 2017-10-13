package me.iblitzkriegi.vixio.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

/**
 * Created by Blitz on 8/9/2017.
 */

/**
 * Created by Blitz on 7/22/2017.
 */
public class DiscordEventHandler extends Event{
    static {
        EventValues.registerEventValue(DiscordEventHandler.class, Channel.class, new Getter<Channel, DiscordEventHandler>() {
            @Override
            public Channel get(DiscordEventHandler event) {
                if (event.getObject("Channel") != null) {
                    if (event.getObject("Channel") instanceof Channel) {
                        Channel m = (Channel) event.getObject("Channel");
                        return m;
                    }
                }
                Skript.error("Event value is not present in requested event.");
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
                Skript.error("Event value is not present in requested event.");
                return null;
            }},0);

    }

    private static final HandlerList hls = new HandlerList();
    private net.dv8tion.jda.core.events.Event event;
    private HashMap<String, Object> values = new HashMap<>();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public DiscordEventHandler(net.dv8tion.jda.core.events.Event e, Object... objects){
        event = e;
        for(Object o : objects){
            if(o instanceof User){
                values.put("User", o);
            }else if(o instanceof Channel){
                values.put("Channel", o);
            }else if(o instanceof String){
                values.put("String", o);
            }else if(o instanceof Guild){
                values.put("Guild", o);
            }
        }

    }
    public net.dv8tion.jda.core.events.Event getEvent(){
        return event;
    }
    public Object getObject(String s){
        if(values.get(s)!=null){
            return values.get(s);
        }
        return null;
    }
    public net.dv8tion.jda.core.events.Event getJdaEvent(){
        return event;
    }


}


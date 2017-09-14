package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.net.URL;

/**
 * Created by Blitz on 11/7/2016.
 */
@EvntAnnotation.Event(
        name = "UserStartStreaming",
        title = "User Start Streaming",
        desc = "Fired when a user starts streaming",
        type = MultiBotGuildCompare.class,
        syntax = "[discord] user start streaming seen by %string%",
        example = "TESTING STUFF")
public class EvntUserStartStreaming extends Event {
    private static final HandlerList hls = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    private User vUser;
    private JDA vJDA;
    private Guild vGuild;
    private URL vUrl;
    private String vCur;
    public EvntUserStartStreaming(User user, JDA jda, Guild guild, URL url, String current){
        vUser = user;
        vJDA = jda;
        vUrl = url;
        vGuild = guild;
        vCur = current;
    }
    public String getEvntCur(){return vCur;}
    public URL getEvntUrl(){return vUrl;}
    public Guild getEvntGuild(){return vGuild;}
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
}

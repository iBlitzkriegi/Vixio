package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
    private String vOld;
    private Guild vGuild;
    private String vCurrent;
    public EvntUserStartStreaming(User user, String previous, JDA jda, Guild guild, String current){
        vUser = user;
        vJDA = jda;
        vOld = previous;
        vGuild = guild;
        vCurrent = current;
    }
    public String getEvntCurrent(){return vCurrent;}
    public Guild getEvntGuild(){return vGuild;}
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
    public String getEvntOld(){
        return vOld;
    }
}

package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/7/2016.
 */
@EvntAnnotation.Event(name = "UserOnlineStatusUpdate", title = "User Online Status Update", desc = "Fired when a user updates their online status.",
        type = MultiBotGuildCompare.class, syntax = "[discord] [user] status change seen by %string%", example = "TESTING STUFF")
public class EvntUserStatusChange extends Event {
    private static final HandlerList hls = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    private OnlineStatus vOnlineStatus;
    private User vUser;
    private JDA vJDA;
    private Guild vGuild;
    public EvntUserStatusChange(User user, OnlineStatus status, Guild g, JDA jda){
        vUser = user;
        vOnlineStatus = status;
        vJDA = jda;
        vGuild = g;
    }
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
    public OnlineStatus getEvntStatus(){
        return vOnlineStatus;
    }
    public Guild getEvntGuild(){
        return vGuild;
    }

}

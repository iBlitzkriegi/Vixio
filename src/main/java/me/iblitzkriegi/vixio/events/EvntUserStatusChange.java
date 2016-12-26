package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/7/2016.
 */
@EvntAnnotation.Event(name = "UserOnlineStatusUpdate", type = MultiBotGuildCompare.class, syntax = "[discord] [user] status change seen by %string%")
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
    private OnlineStatus vOldOnlineStatus;
    private User vUser;
    private JDA vJDA;
    public EvntUserStatusChange(User eventuser, OnlineStatus newstatus, OnlineStatus oldstatus, JDA jda){
        vUser = eventuser;
        vOnlineStatus = newstatus;
        vOldOnlineStatus = oldstatus;
        vJDA = jda;
    }
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
    public OnlineStatus getEvntOldStatus(){
        return vOldOnlineStatus;
    }
    public OnlineStatus getEvntNewStatus(){
        return vOnlineStatus;
    }

}

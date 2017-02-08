package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/7/2016.
 */
@EvntAnnotation.Event(name = "UserAvatarUpdate", type = MultiBotGuildCompare.class, syntax = "[discord] user avatar update seen by %string%")
public class EvntUserAvatarUpdate extends Event {
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
    public EvntUserAvatarUpdate(User user, String previous, JDA jda){
        vUser = user;
        vJDA = jda;
        vOld = previous;
    }
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
    public String getOld(){
        return vOld;
    }
}

package me.iblitzkriegi.vixio.events;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/21/2016.
 */
public class EvntGuildMemberLeave extends Event{
    private static final HandlerList hls = new HandlerList();
    private User sUser;
    private Guild sGuild;
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public EvntGuildMemberLeave(User user, Guild guild){
        sUser = user;
        sGuild = guild;
    }
    public User getEvntUser(){
        return sUser;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    public String getEvntUserMention(){
        return sUser.getAsMention();
    }
}

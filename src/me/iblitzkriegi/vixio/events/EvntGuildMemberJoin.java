package me.iblitzkriegi.vixio.events;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 10/22/2016.
 */
public class EvntGuildMemberJoin extends Event{
    private static final HandlerList hls = new HandlerList();
    private Guild sGuild;
    private User sUser;
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList(){
        return hls;
    }
    public EvntGuildMemberJoin(User user, Guild guild){
        sGuild = guild;
        sUser = user;
    }
    public String getEvntUser(){
        return sUser.getId();
    }
    public User getEvntUserObj(){
        return sUser;
    }
    public String getEvntUserMention(){
        return sUser.getAsMention();
    }
    public Guild getEvntGuild(){
        return sGuild;
    }

}

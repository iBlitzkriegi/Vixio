package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(name = "GuildMemberLeave", type = MultiBotGuildCompare.class, syntax = "[discord] guild member leave seen by %string%")
public class EvntGuildMemberLeave extends Event {
    private static final HandlerList hls = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    private User vUser;
    private Guild vGuild;
    private JDA vJDA;
    public EvntGuildMemberLeave(User user, Guild guild, JDA jda){
        vUser = user;
        vGuild = guild;
        vJDA = jda;
    }
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
    public User getEvntBot(){
        return vJDA.getSelfUser();
    }
    public Guild getEvntGuild(){
        return vGuild;
    }
}

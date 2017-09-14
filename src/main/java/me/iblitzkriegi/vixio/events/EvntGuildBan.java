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
        name = "UserMemberBan",
        title = "User Member Ban",
        desc = "Fired when a user gets banned from a Guild",
        type = MultiBotGuildCompare.class,
        syntax = "[discord] member ban event seen by %string%",
        example = "on member ban event seen by \\\"Rawr\\\":\\n" +
                "\\tbroadcast \\\"%event-user% is outta here!\\\"")
public class EvntGuildBan extends Event {
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
    public EvntGuildBan(User user, Guild guild, JDA jda){
        vUser = user;
        vJDA = jda;
        vGuild = guild;
    }
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntUser(){
        return vUser;
    }
    public Guild getEvntGuild(){return vGuild;}
}

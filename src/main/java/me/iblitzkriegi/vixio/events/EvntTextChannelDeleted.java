package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.annotation.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(name = "TextChannelDelete", title = "Text Channel Delete", desc = "Fired when a text-channel is deleted",  type = MultiBotGuildCompare.class, syntax = "[discord] text[-]channel deleted seen by %string%", example = "TESTING STUFF")
public class EvntTextChannelDeleted extends Event {
    private static final HandlerList hls = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    private Guild vGuild;
    private JDA vJDA;
    private Channel vChannel;
    public EvntTextChannelDeleted(Channel channel, Guild guild, JDA jda){
        vChannel = channel;
        vGuild = guild;
        vJDA = jda;
    }
    public JDA getEvntJDA(){
        return vJDA;
    }
    public User getEvntBot(){
        return vJDA.getSelfUser();
    }
    public Guild getEvntGuild(){
        return vGuild;
    }
    public Channel getEvntChannel(){
        return vChannel;
    }
}

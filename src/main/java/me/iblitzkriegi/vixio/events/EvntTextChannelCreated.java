package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.time.OffsetDateTime;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(name = "TextChannelCreate", title = "Text Channel Create", desc = "Fired when a text-channel is created",  type = MultiBotGuildCompare.class, syntax = "[discord] text[-]channel created seen by %string%", example = "TESTING STUFF")
public class EvntTextChannelCreated extends Event {
    private static final HandlerList hls = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    private OffsetDateTime vCreated;
    private Guild vGuild;
    private JDA vJDA;
    private Channel vChannel;
    public EvntTextChannelCreated(Channel channel, Guild guild, JDA jda, OffsetDateTime created){
        vChannel = channel;
        vGuild = guild;
        vJDA = jda;
        vCreated = created;
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
    public OffsetDateTime getCreationTime(){
        return vCreated;
    }
    public Channel getEvntChannel(){
        return vChannel;
    }
}

package me.iblitzkriegi.vixio.jda;

import me.iblitzkriegi.vixio.events.EventJDAEvent;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.Objects;

/**
 * Created by Blitz on 7/28/2017.
 */
public class GenericJDAEvent extends ListenerAdapter {
    @Override
    public void onGenericEvent(Event e){
        if(e instanceof GuildMessageReceivedEvent){
            EventJDAEvent event = new EventJDAEvent(e, ((GuildMessageReceivedEvent) e).getChannel(), ((GuildMessageReceivedEvent) e).getAuthor(), ((GuildMessageReceivedEvent) e).getGuild(), ((GuildMessageReceivedEvent) e).getMessage(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }else if(e instanceof VoiceChannelCreateEvent){
            EventJDAEvent event = new EventJDAEvent(e, ((VoiceChannelCreateEvent) e).getChannel(), ((VoiceChannelCreateEvent) e).getGuild(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }else if(e instanceof GuildMemberJoinEvent){
            EventJDAEvent event = new EventJDAEvent(e, ((GuildMemberJoinEvent) e).getUser(), e.getJDA(), ((GuildMemberJoinEvent) e).getGuild());
            Bukkit.getPluginManager().callEvent(event);
        }else if(e instanceof GuildMemberLeaveEvent){
            EventJDAEvent event = new EventJDAEvent(e, ((GuildMemberLeaveEvent) e).getUser(), e.getJDA(), ((GuildMemberLeaveEvent) e).getGuild());
            Bukkit.getPluginManager().callEvent(event);
        }else if(e instanceof GuildVoiceJoinEvent){
            EventJDAEvent event = new EventJDAEvent(e, ((GuildVoiceJoinEvent) e).getMember().getUser(), ((GuildVoiceJoinEvent) e).getChannelJoined(), ((GuildVoiceJoinEvent) e).getGuild(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }else if(e instanceof GuildVoiceMoveEvent){
            EventJDAEvent event = new EventJDAEvent(e, ((GuildVoiceMoveEvent) e).getGuild(), ((GuildVoiceMoveEvent) e).getChannelJoined(), ((GuildVoiceMoveEvent) e).getMember().getUser(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}

package me.iblitzkriegi.vixio.jda;

import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 8/9/2017.
 */
public class DiscordEventHandlerListener extends ListenerAdapter {
    @Override
    public void onGenericEvent(Event e) {
        DiscordEventHandler event;
        if(e instanceof GuildVoiceLeaveEvent){
            event = new DiscordEventHandler(e, ((GuildVoiceLeaveEvent) e).getChannelLeft(), ((GuildVoiceLeaveEvent) e).getGuild(), ((GuildVoiceLeaveEvent) e).getMember().getUser(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }else if(e instanceof GuildMemberNickChangeEvent){
            event = new DiscordEventHandler(e, ((GuildMemberNickChangeEvent) e).getPrevNick(), ((GuildMemberNickChangeEvent) e).getGuild(), ((GuildMemberNickChangeEvent) e).getUser(), ((GuildMemberNickChangeEvent) e).getMember(), e.getJDA().getSelfUser(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}

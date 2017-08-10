package me.iblitzkriegi.vixio.jda;

import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 8/9/2017.
 */
public class DiscordEventHandlerListener extends ListenerAdapter {
    @Override
    public void onGenericEvent(Event e) {
        if(e instanceof GuildVoiceLeaveEvent){
            DiscordEventHandler event = new DiscordEventHandler(e, ((GuildVoiceLeaveEvent) e).getChannelLeft(), ((GuildVoiceLeaveEvent) e).getGuild(), ((GuildVoiceLeaveEvent) e).getMember().getUser(), e.getJDA());
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}

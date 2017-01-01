package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntUserJoinVc;
import me.iblitzkriegi.vixio.events.EvntUserLeaveVc;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 12/31/2016.
 */
public class UserLeaveVc extends ListenerAdapter{
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e){
        EvntUserLeaveVc efc = new EvntUserLeaveVc(e.getMember().getUser(), e.getGuild(), e.getJDA(), e.getChannelLeft());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

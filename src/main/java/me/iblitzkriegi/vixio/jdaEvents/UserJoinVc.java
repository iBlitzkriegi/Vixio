package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntUserJoinVc;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 12/31/2016.
 */
public class UserJoinVc extends ListenerAdapter{
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e){
        EvntUserJoinVc efc = new EvntUserJoinVc(e.getMember().getUser(), e.getGuild(), e.getJDA(), e.getChannelJoined());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 10/22/2016.
 */
public class GuildMemberJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        EvntGuildMemberJoin efc = new EvntGuildMemberJoin(e.getUser(), e.getGuild());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

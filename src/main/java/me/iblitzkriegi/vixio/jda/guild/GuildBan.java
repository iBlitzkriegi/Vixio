package me.iblitzkriegi.vixio.jda.guild;

import me.iblitzkriegi.vixio.events.member.EvntGuildBan;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 2/5/2017.
 */
public class GuildBan extends ListenerAdapter {
    @Override
    public void onGuildBan(GuildBanEvent e){
        EvntGuildBan efc = new EvntGuildBan(e.getUser(), e.getGuild(), e.getJDA());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

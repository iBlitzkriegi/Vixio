package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Created by Blitz on 10/21/2016.
 */
public class GuildMemberLeave extends ListenerAdapter {
    public static HashMap<String, String> heWhoLeft = new HashMap<>();
    public static HashMap<String, Guild> heWhoLeftsGuild = new HashMap<>();
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e){
        User user = e.getUser();
        Guild guild = e.getGuild();
        EvntGuildMemberLeave efc = new EvntGuildMemberLeave(user, guild);
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

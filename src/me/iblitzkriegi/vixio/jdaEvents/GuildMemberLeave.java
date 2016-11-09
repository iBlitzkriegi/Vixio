package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 11/4/2016.
 */
public class GuildMemberLeave extends ListenerAdapter {
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e){
        System.out.println("rawrXD");
        if(!e.getUser().getId().equalsIgnoreCase(e.getJDA().getSelfInfo().getId())){
            User user = e.getUser();
            JDA jda = e.getJDA();
            Guild guild = e.getGuild();
            EvntGuildMemberLeave efc = new EvntGuildMemberLeave(user, guild, jda);
            Bukkit.getServer().getPluginManager().callEvent(efc);
        }
    }
}

package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 11/4/2016.
 */
public class GuildMemberLeave extends ListenerAdapter {
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e){
        if(!e.getMember().getUser().getId().equalsIgnoreCase(e.getJDA().getSelfUser().getId())){
            User user = e.getMember().getUser();
            JDA jda = e.getJDA();
            Guild guild = e.getGuild();
            EvntGuildMemberLeave efc = new EvntGuildMemberLeave(user, guild, jda);
            Bukkit.getServer().getPluginManager().callEvent(efc);
        }
    }
}

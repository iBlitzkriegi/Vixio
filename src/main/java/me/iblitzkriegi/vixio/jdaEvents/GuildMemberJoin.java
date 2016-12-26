package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 11/4/2016.
 */
public class GuildMemberJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        if(!e.getJDA().getSelfUser().getId().equalsIgnoreCase(e.getMember().getUser().getId())){
            User user = e.getMember().getUser();
            JDA jda = e.getJDA();
            Guild guild = e.getGuild();
            EvntGuildMemberJoin efc = new EvntGuildMemberJoin(user, guild, jda);
            Bukkit.getServer().getPluginManager().callEvent(efc);


        }
    }
}

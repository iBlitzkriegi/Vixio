package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 10/15/2016.
 */
public class GuildMessageReceived extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if(!e.getAuthor().getId().equals(e.getJDA().getSelfInfo().getId())) {
            User author = e.getAuthor();
            TextChannel channel = e.getChannel();
            if (e.getMessage().getMentionedUsers().size() > 0) {
                User mentioned = e.getMessage().getMentionedUsers().get(0);
                EvntGuildMsgReceived efc = new EvntGuildMsgReceived(author, channel, e.getMessage(), mentioned, e.getGuild());
                Bukkit.getServer().getPluginManager().callEvent(efc);
                return;
            } else {
                EvntGuildMsgReceived efc = new EvntGuildMsgReceived(author, channel, e.getMessage(), null, e.getGuild());
                Bukkit.getServer().getPluginManager().callEvent(efc);
            }
        }
    }
}


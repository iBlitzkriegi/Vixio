package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 10/30/2016.
 */
public class GuildMessageReceived extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if(!e.getJDA().getSelfInfo().getId().equalsIgnoreCase(e.getAuthor().getId())){
            User author = e.getAuthor();
            TextChannel channel = e.getChannel();
            User bot = e.getJDA().getUserById(e.getJDA().getSelfInfo().getId());
            if (e.getMessage().getMentionedUsers().size() > 0) {
                User mentioned = e.getMessage().getMentionedUsers().get(0);
                EvntGuildMessageReceive efc = new EvntGuildMessageReceive(author, channel, e.getMessage(), mentioned, e.getGuild(), bot, e.getJDA());
                Bukkit.getServer().getPluginManager().callEvent(efc);
                return;
            } else {
                EvntGuildMessageReceive efc = new EvntGuildMessageReceive(author, channel, e.getMessage(), null, e.getGuild(), bot, e.getJDA());
                Bukkit.getServer().getPluginManager().callEvent(efc);
            }
        }
    }
}

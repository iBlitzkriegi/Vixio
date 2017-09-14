package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Blitz on 10/30/2016.
 */
public class GuildMessageReceived extends ListenerAdapter {
    public static HashMap<User, Message> getLast = new HashMap<>();
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if(!e.getJDA().getSelfUser().getId().equalsIgnoreCase(e.getAuthor().getId())){
            User author = e.getAuthor();
            TextChannel channel = e.getChannel();
            User bot = e.getJDA().getUserById(e.getJDA().getSelfUser().getId());
            if (e.getMessage().getMentionedUsers().size() > 0) {
                List list = e.getMessage().getMentionedUsers();
                EvntGuildMessageReceive efc = new EvntGuildMessageReceive(author, channel, e.getMessage(), list, e.getGuild(), bot, e.getJDA());
                Bukkit.getServer().getPluginManager().callEvent(efc);
                return;
            } else {
                EvntGuildMessageReceive efc = new EvntGuildMessageReceive(e.getAuthor(), e.getChannel(), e.getMessage(), null, e.getGuild(), e.getJDA().getSelfUser(), e.getJDA());
                Bukkit.getServer().getPluginManager().callEvent(efc);
            }
        }
    }
}

package me.iblitzkriegi.vixio.jda.message;

import me.iblitzkriegi.vixio.events.message.EvntGuildMessageBotSend;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 10/30/2016.
 */
public class BotSendGuildMessage extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if(e.getJDA().getSelfUser().getId().equalsIgnoreCase(e.getAuthor().getId())){
            User author = e.getAuthor();
            TextChannel channel = e.getChannel();
            User bot = e.getJDA().getUserById(e.getJDA().getSelfUser().getId());
            EvntGuildMessageBotSend efc = new EvntGuildMessageBotSend(author, channel, e.getMessage(), e.getGuild(), bot, e.getJDA(), e.getChannel());
            Bukkit.getServer().getPluginManager().callEvent(efc);
            return;
        }

    }
}

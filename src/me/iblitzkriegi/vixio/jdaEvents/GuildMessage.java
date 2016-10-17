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
public class GuildMessage extends ListenerAdapter {
        @Override
        public void onGuildMessageReceived(GuildMessageReceivedEvent e){
            User author = e.getAuthor();
            TextChannel channel = e.getChannel();
            String msg = e.getMessage().getContent();
            EvntGuildMsgReceived efc = new EvntGuildMsgReceived(author, channel, msg);
            Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}


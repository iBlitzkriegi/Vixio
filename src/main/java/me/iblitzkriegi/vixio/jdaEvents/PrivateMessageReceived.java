package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceive;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 10/30/2016.
 */
public class PrivateMessageReceived extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
        if(!e.getAuthor().getId().equals(e.getJDA().getSelfUser().getId())) {
            User author = e.getAuthor();
            PrivateChannel channel = e.getChannel();
            User bot = e.getJDA().getUserById(e.getJDA().getSelfUser().getId());
            if (e.getMessage().getMentionedUsers().size() > 0) {
                EvntPrivateMessageReceive efc = new EvntPrivateMessageReceive(author, channel, e.getMessage(), bot, e.getJDA());
                Bukkit.getServer().getPluginManager().callEvent(efc);
                return;
            } else {
                EvntPrivateMessageReceive efc = new EvntPrivateMessageReceive(author, channel, e.getMessage() , bot, e.getJDA());
                Bukkit.getServer().getPluginManager().callEvent(efc);
            }
        }
    }
}

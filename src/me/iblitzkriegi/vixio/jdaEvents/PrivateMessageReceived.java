package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 10/16/2016.
 */
public class PrivateMessageReceived extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e){
        User author = e.getAuthor();
        PrivateChannel channel = e.getChannel();
        String msg = e.getMessage().getContent();
        EvntPrivateMessageReceived efc = new EvntPrivateMessageReceived(author, channel, e.getMessage());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

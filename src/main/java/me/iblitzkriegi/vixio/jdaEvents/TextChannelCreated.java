package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntTextChannelCreated;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 2/5/2017.
 */
public class TextChannelCreated extends ListenerAdapter {
    @Override
    public void onTextChannelCreate(TextChannelCreateEvent e){
        EvntTextChannelCreated efc = new EvntTextChannelCreated(e.getChannel(), e.getGuild(), e.getJDA(), e.getChannel().getCreationTime());
        Bukkit.getServer().getPluginManager().callEvent(efc);

    }
}

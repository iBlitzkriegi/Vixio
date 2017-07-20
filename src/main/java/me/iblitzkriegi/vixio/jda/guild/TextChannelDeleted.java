package me.iblitzkriegi.vixio.jda.guild;

import me.iblitzkriegi.vixio.events.EvntTextChannelDeleted;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 2/5/2017.
 */
public class TextChannelDeleted extends ListenerAdapter {
    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent e){
        EvntTextChannelDeleted efc = new EvntTextChannelDeleted(e.getChannel(), e.getGuild(), e.getJDA());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

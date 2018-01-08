package me.iblitzkriegi.vixio.jda;

import me.iblitzkriegi.vixio.events.EvntMessageReceived;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class JDAEventListener extends ListenerAdapter{
    @Override
    public void onGenericEvent(Event e){
        if(e instanceof GuildMessageReceivedEvent){
            EvntMessageReceived efc = new EvntMessageReceived(((GuildMessageReceivedEvent) e).getMember(), ((GuildMessageReceivedEvent) e).getChannel(), ((GuildMessageReceivedEvent) e).getMessage(), e.getJDA(), ((GuildMessageReceivedEvent) e).getGuild());
            Bukkit.getServer().getPluginManager().callEvent(efc);
        }
    }
}

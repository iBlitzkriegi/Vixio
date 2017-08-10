package me.iblitzkriegi.vixio.jda;

import me.iblitzkriegi.vixio.events.EventGuildMessageReceived;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 7/22/2017.
 */
public class GuildMessageReceived extends ListenerAdapter{
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        if(!e.getJDA().getSelfUser().getId().equalsIgnoreCase(e.getAuthor().getId())){
            Boolean t = e.getMessage().getMentionedUsers()!=null ? true : false;
            EventGuildMessageReceived evnt;
            if(t){
                evnt = new EventGuildMessageReceived(e.getAuthor(), e.getChannel(), e.getMessage(), e.getMessage().getMentionedUsers(), e.getGuild(), e.getJDA().getSelfUser(), e.getJDA());
            }else{
                 evnt = new EventGuildMessageReceived(e.getAuthor(), e.getChannel(), e.getMessage(), null, e.getGuild(), e.getJDA().getSelfUser(), e.getJDA());
            }
            Bukkit.getServer().getPluginManager().callEvent(evnt);
        }
    }
}

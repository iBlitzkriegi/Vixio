package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntUserStartStreaming;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.user.UserGameUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 2/12/2017.
 */
public class UserStartStreaming extends ListenerAdapter{
    @Override
    public void onUserGameUpdate(UserGameUpdateEvent e){
        if(e.getGuild().getMember(e.getUser()).getGame().getType() == Game.GameType.TWITCH){
            EvntUserStartStreaming efc = new EvntUserStartStreaming(e.getUser(), e.getPreviousGame().getName(), e.getJDA(), e.getGuild(), e.getGuild().getMember(e.getUser()).getGame().getName());
            Bukkit.getPluginManager().callEvent(efc);
        }
    }
}

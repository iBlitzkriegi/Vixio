package me.iblitzkriegi.vixio.jda.member;

import me.iblitzkriegi.vixio.events.member.EvntUserStartStreaming;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.user.UserGameUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Blitz on 2/12/2017.
 */
public class UserStartStreaming extends ListenerAdapter{
    @Override
    public void onUserGameUpdate(UserGameUpdateEvent e){
        if(e.getGuild().getMember(e.getUser()).getGame().getType() == Game.GameType.TWITCH){
            try {
                URL url = new URL(e.getGuild().getMember(e.getUser()).getGame().getUrl().toString());
                EvntUserStartStreaming efc = new EvntUserStartStreaming(e.getUser(), e.getJDA(), e.getGuild(), url, e.getGuild().getMember(e.getUser()).getGame().getName());
                Bukkit.getPluginManager().callEvent(efc);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
    }
}

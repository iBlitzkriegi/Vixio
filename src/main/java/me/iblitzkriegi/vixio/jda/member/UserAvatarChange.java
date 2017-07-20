package me.iblitzkriegi.vixio.jda.member;

import me.iblitzkriegi.vixio.events.member.EvntUserAvatarUpdate;
import net.dv8tion.jda.core.events.user.UserAvatarUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 2/5/2017.
 */
public class UserAvatarChange extends ListenerAdapter {
    @Override
    public void onUserAvatarUpdate(UserAvatarUpdateEvent e){
        EvntUserAvatarUpdate efc = new EvntUserAvatarUpdate(e.getUser(), e.getPreviousAvatarUrl(), e.getJDA());
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

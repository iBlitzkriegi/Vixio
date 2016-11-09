package me.iblitzkriegi.vixio.jdaEvents;

import me.iblitzkriegi.vixio.events.EvntUserStatusChange;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 11/7/2016.
 */
public class UserUpdateStatus extends ListenerAdapter {
    @Override
    public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent e){
        User user = e.getUser();
        JDA jda = e.getJDA();
        OnlineStatus oldStatus = e.getPreviousOnlineStatus();
        OnlineStatus newStatus = e.getUser().getOnlineStatus();
        EvntUserStatusChange efc = new EvntUserStatusChange(user, newStatus, oldStatus, jda);
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

package me.iblitzkriegi.vixio.events;

import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 2/15/2017.
 */
@EvntAnnotation.Event(
        name = "MessageAddReaction",
        title = "Message Add Reaction",
        desc = "Fired when a user adds a reaction",
        type = MultiBotGuildCompare.class,
        syntax = "[discord] add reaction to message seen by [bot] %string%",
        example = "TESTING STUFF")
public class EvntMessageAddReaction extends Event {
    private static final HandlerList hls = new HandlerList();
    private boolean cancel = false;
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }

    private JDA vJDA;
    private User vUser;
    private Message vMessage;
    private String vEmoji;
    private Channel vChannel;

    public EvntMessageAddReaction(JDA jda, User user, Message message, String emoji, Channel channel) {
        vJDA = jda;
        vUser = user;
        vMessage = message;
        vEmoji = emoji;
        vChannel = channel;
    }

    public JDA getEvntJDA() {
        return vJDA;
    }

    public String getEvntEmoji() {
        return vEmoji;
    }

    public User getBot() {
        return vJDA.getSelfUser();
    }

    public User getEvntUser() {
        return vUser;
    }

    public Channel getEvntChannel() {
        return vChannel;
    }

    public Message getEvntMessage() {
        return vMessage;
    }
}

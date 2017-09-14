package me.iblitzkriegi.vixio.jdaEvents;

import com.vdurmont.emoji.EmojiParser;
import me.iblitzkriegi.vixio.events.EvntMessageAddReaction;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 2/15/2017.
 */
public class MessageAddReaction extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e){
        if (!e.getChannel().getType().equals(ChannelType.TEXT))
            return;
        TextChannel channel = (TextChannel) e.getChannel();
        e.getJDA().getTextChannelById(e.getChannel().getId());
        EvntMessageAddReaction efc = new EvntMessageAddReaction(e.getJDA(), e.getUser(), e.getChannel().getMessageById(e.getMessageId()).complete(), EmojiParser.parseToAliases(e.getReaction().getEmote().getName()), channel);
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
}

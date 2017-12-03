package me.iblitzkriegi.vixio.jda;

import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import me.iblitzkriegi.vixio.expressions.eventValues.ExprTheLastTopic;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

/**
 * Created by Blitz on 8/9/2017.
 */
public class DiscordEventHandlerListener extends ListenerAdapter {
    @Override
    public void onGenericEvent(Event e) {
        if(Vixio.getInstance().patterns.contains(Vixio.getPattern(e.getClass()))) {
            DiscordEventHandler event;
            if (e instanceof GuildVoiceLeaveEvent) {
                event = new DiscordEventHandler(e, ((GuildVoiceLeaveEvent) e).getChannelLeft(), ((GuildVoiceLeaveEvent) e).getGuild(), ((GuildVoiceLeaveEvent) e).getMember().getUser(), e.getJDA(), ((GuildVoiceLeaveEvent) e).getMember());
                Bukkit.getPluginManager().callEvent(event);
                return;
            }else if (e instanceof GuildMessageReceivedEvent) {
                event = new DiscordEventHandler(e, ((GuildMessageReceivedEvent) e).getAuthor(), ((GuildMessageReceivedEvent) e).getMember(), ((GuildMessageReceivedEvent) e).getChannel(), ((GuildMessageReceivedEvent) e).getGuild(), e.getJDA(), ((GuildMessageReceivedEvent) e).getMessage(), ((GuildMessageReceivedEvent) e).getMessage().getContent());
                Bukkit.getPluginManager().callEvent(event);
                return;
            }else if(e instanceof GuildVoiceJoinEvent){
                event = new DiscordEventHandler(e, ((GuildVoiceJoinEvent) e).getChannelJoined(), ((GuildVoiceJoinEvent) e).getGuild(), ((GuildVoiceJoinEvent) e).getMember(), e.getJDA(), ((GuildVoiceJoinEvent) e).getMember().getUser());
                Bukkit.getPluginManager().callEvent(event);
                return;
            }else if(e instanceof TextChannelUpdateTopicEvent){
                ExprTheLastTopic.oldTopic = ((TextChannelUpdateTopicEvent) e).getOldTopic();
                ExprTheLastTopic.newTopic = ((TextChannelUpdateTopicEvent) e).getChannel().getTopic();
                event = new DiscordEventHandler(e, ((TextChannelUpdateTopicEvent) e).getChannel(), ((TextChannelUpdateTopicEvent) e).getGuild(), e.getJDA());
                Bukkit.getPluginManager().callEvent(event);
                return;
            }
        }
    }
}

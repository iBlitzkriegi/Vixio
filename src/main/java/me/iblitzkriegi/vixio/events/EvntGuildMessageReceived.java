package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleEvent;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class EvntGuildMessageReceived extends Event{
    private static final HandlerList h1s = new HandlerList();
    private User vAuthor;
    private Channel vChannel;
    private Message vMsg;
    private List<?> vMentioned;
    private Guild vGuild;
    private User vBot;
    private JDA vJDA;

    static {
        Vixio.registerEvent("GuildMessageRec", SkriptEvent.class, EvntGuildMessageReceived.class, "guild message receive[d]");
    }

    @Override
    public HandlerList getHandlers() {
        return h1s;
    }
    public EvntGuildMessageReceived(User author, Channel channel, Message msg, List<?> mentioned, Guild guild, User bot, JDA jda){
        vAuthor = author;
        vChannel = channel;
        vMsg = msg;
        vMentioned = mentioned;
        vGuild = guild;
        vBot = bot;
        vJDA = jda;
    }

}

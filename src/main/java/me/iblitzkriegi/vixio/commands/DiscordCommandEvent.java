package me.iblitzkriegi.vixio.commands;

import me.iblitzkriegi.vixio.events.base.SimpleBukkitEvent;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Cancellable;

public class DiscordCommandEvent extends SimpleBukkitEvent implements Cancellable {

    private DiscordCommand command;
    private Guild guild;
    private Message message;
    private User user;
    private Member member;
    private MessageChannel messagechannel;
    private String prefix;
    private String usedAlias;
    private boolean cancelled;
    private Channel channel;
    private Bot bot;

    public DiscordCommandEvent(String prefix, String usedAlias, DiscordCommand command, Guild guild,
                               MessageChannel messagechannel, Channel channel, Message message, User user,
                               Member member, Bot bot) {
        this.command = command;
        this.guild = guild;
        this.user = user;
        this.usedAlias = usedAlias;
        this.message = message;
        this.member = member;
        this.channel = channel;
        this.messagechannel = messagechannel;
        this.prefix = prefix;
        this.bot = bot;
    }

    public DiscordCommand getCommand() {
        return command;
    }

    public Guild getGuild() {
        return guild;
    }

    public Message getMessage() {
        return message;
    }

    public Member getMember() {
        return member;
    }

    public User getUser() {
        return user;
    }

    public Channel getChannel() {
        return channel;
    }

    public MessageChannel getMessageChannel() {
        return messagechannel;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUsedAlias() {
        return usedAlias;
    }

    public Bot getBot() {
        return bot;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}

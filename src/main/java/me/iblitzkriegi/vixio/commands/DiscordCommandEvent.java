package me.iblitzkriegi.vixio.commands;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DiscordCommandEvent extends Event {

    private final static HandlerList handlers = new HandlerList();
    private DiscordCommand command;
    private Guild guild;
    private Message message;
    private User user;
    private Member member;
    private MessageChannel channel;
    private String prefix;
    private String usedAlias;

    public DiscordCommandEvent(String prefix, String usedAlias, DiscordCommand command, Guild guild, MessageChannel channel, Message message, User user, Member member) {
        this.command = command;
        this.guild = guild;
        this.user = user;
        this.usedAlias = usedAlias;
        this.message = message;
        this.member = member;
        this.channel = channel;
        this.prefix = prefix;
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

    public MessageChannel getChannel() {
        return channel;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUsedAlias() {
        return usedAlias;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

package me.iblitzkriegi.vixio.commands;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DiscordCommandEvent extends Event {

    private DiscordCommand command;
    private Guild guild;
    private Message message;
    private User user;
    private Member member;
    private MessageChannel channel;
    private String prefix;

    public DiscordCommandEvent(String prefix, DiscordCommand command, Guild guild, TextChannel channel, Message message, User user, Member member) {
        this.command = command;
        this.guild = guild;
        this.user = user;
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

    private final static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

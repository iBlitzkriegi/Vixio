package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.command.ScriptCommand;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DiscordCommandEvent extends Event {

    private DiscordCommand command;
    private Guild guild;
    private Message message;
    private User user;
    private Member member;

    public DiscordCommandEvent(DiscordCommand command, Guild guild, Message message, User user) {
        this.command = command;
        this.guild = guild;
        this.user = user;
        this.message = message;
        if (guild != null && user != null)
            this.member = guild.getMember(user);
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

    private final static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

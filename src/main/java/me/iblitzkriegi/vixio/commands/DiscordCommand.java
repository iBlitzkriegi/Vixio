package me.iblitzkriegi.vixio.commands;

import java.io.File;

import java.util.List;

import ch.njol.skript.command.Argument;

import ch.njol.skript.command.ScriptCommandEvent;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.ChannelType;

import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.util.Utils;
import ch.njol.util.Validate;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

public class DiscordCommand {

    private String name;
    private List<String> aliases;
    private List<String> roles;
    private List<ChannelType> executeableIn;
    private String description;
    private String usage;
    private String pattern;

    private Trigger trigger;

    private List<DiscordArgument<?>> arguments;

    public DiscordCommand(File script, String name, String pattern, List<DiscordArgument<?>> arguments, String[] prefixes,
                          List<String> aliases, String description, String usage, List<String> roles,
                          List<ChannelType> executableIn, List<TriggerItem> items) {
        Validate.notNull(name, pattern, arguments, description, usage, aliases, items);
        this.name = name;
        aliases.removeIf(alias -> alias.equalsIgnoreCase(name));
        this.aliases = aliases;
        this.roles = roles;
        this.executeableIn = executableIn;
        this.description = Utils.replaceEnglishChatStyles(description);
        this.usage = Utils.replaceEnglishChatStyles(usage);
        this.pattern = pattern;
        this.arguments = arguments;

        trigger = new Trigger(script, "discord command " + name, new SimpleEvent(), items);

    }

    public boolean execute(String command, String args, Guild guild, Message message, User user, ChannelType channelType) {
        DiscordCommandEvent event = new DiscordCommandEvent(this, guild, message, user);

        ParseLogHandler log = SkriptLogger.startParseLogHandler();

        try {

            boolean ok = DiscordCommands.parseArguments(args, this, event);
            if (!ok)
                return false;

            trigger.execute(event);

        } finally {
            log.stop();
        }

        return true;
    }

    public List<DiscordArgument<?>> getArguments() {
        return arguments;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public File getScript() {
        return trigger.getScript();
    }

}
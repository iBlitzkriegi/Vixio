package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Utils;
import ch.njol.util.Validate;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DiscordCommand {

    private String name;
    private List<String> aliases;
    private List<String> roles;
    private List<ChannelType> executeableIn;
    private List<String> prefixes;
    private String description;
    private String usage;
    private String pattern;

    private Trigger trigger;

    private List<DiscordArgument<?>> arguments;

    public DiscordCommand(File script, String name, String pattern, List<DiscordArgument<?>> arguments, List<String> prefixes,
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
        this.prefixes = prefixes;
        this.arguments = arguments;

        trigger = new Trigger(script, "discord command " + name, new SimpleEvent(), items);

    }

    public boolean execute(String command, String prefix, String args, Guild guild, TextChannel channel, Message message, User user,
                           Member member) {
        DiscordCommandEvent event = new DiscordCommandEvent(prefix, this, guild, channel, message, user, member);
        if (args == null)
            args = "";


        ParseLogHandler log = SkriptLogger.startParseLogHandler();

        try {

            boolean ok = DiscordCommands.getInstance().parseArguments(args, this, event);
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

    public List<String> getPrefixes() {
        return prefixes;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public List<String> getUsableAliases() {
        List<String> usableAliases = new ArrayList<String>(getAliases());
        usableAliases.add(getName());
        return usableAliases;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public List<ChannelType> getExecuteableIn() {
        return executeableIn;
    }

    public List<String> getRoles() {
        return roles;
    }

    public File getScript() {
        return trigger.getScript();
    }

}
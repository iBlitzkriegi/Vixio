package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Utils;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
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
    private List<String> bots;

    private Trigger trigger;

    private List<DiscordArgument<?>> arguments;

    public DiscordCommand(File script, String name, String pattern, List<DiscordArgument<?>> arguments, List<String> prefixes,
                          List<String> aliases, String description, String usage, List<String> roles,
                          List<ChannelType> executableIn, List<String> bots, List<TriggerItem> items) {
        this.name = name;
        if (aliases != null)
            aliases.removeIf(alias -> alias.equalsIgnoreCase(name));
        this.aliases = aliases;
        this.roles = roles;
        this.executeableIn = executableIn;
        this.description = Utils.replaceEnglishChatStyles(description);
        this.usage = Utils.replaceEnglishChatStyles(usage);
        this.pattern = pattern;
        this.prefixes = prefixes;
        this.bots = bots;
        this.arguments = arguments;

        trigger = new Trigger(script, "discord command " + name, new SimpleEvent(), items);

    }

    public boolean execute(String prefix, String alias, String args, Guild guild, MessageChannel channel, Message message, User user,
                           Member member, JDA jda) {
        DiscordCommandEvent event = new DiscordCommandEvent(prefix, alias, this, guild, channel, message, user, member);
        if (args == null)
            args = "";


        ParseLogHandler log = SkriptLogger.startParseLogHandler();

        try {

            Bot bot = Vixio.getInstance().botHashMap.get(jda);
            boolean ok = DiscordCommandFactory.getInstance().parseArguments(args, this, event);

            if (!ok)
                return false;
            else if (!this.getExecutableIn().contains(channel.getType()))
                return false;
            else if (this.getRoles() != null && member != null)
                if (member.getRoles().stream().noneMatch(r -> this.getRoles().contains(r.getName())))
                    return false;
                else if (bots != null && !bots.contains(bot.getName()))
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
        List<String> usableAliases = new ArrayList<>();
        usableAliases.add(getName());
        if (getAliases() != null)
            usableAliases.addAll(getAliases());
        return usableAliases;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public List<ChannelType> getExecutableIn() {
        return executeableIn;
    }

    public List<String> getRoles() {
        return roles;
    }

    public File getScript() {
        return trigger.getScript();
    }

}
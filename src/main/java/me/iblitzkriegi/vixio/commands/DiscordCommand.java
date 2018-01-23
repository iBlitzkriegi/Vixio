package me.iblitzkriegi.vixio.commands;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.command.Argument;
import ch.njol.skript.command.Commands;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.NonNullPair;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.ChannelType;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.command.Commands.CommandAliasHelpTopic;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.localization.Language;
import ch.njol.skript.localization.Message;
import ch.njol.skript.log.LogEntry;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.log.Verbosity;
import ch.njol.skript.util.EmptyStacktraceException;
import ch.njol.skript.util.Utils;
import ch.njol.util.StringUtils;
import ch.njol.util.Validate;

public class DiscordCommand {

    private String name;
    private List<String> aliases;
    private List<String> roles;
    private List<ChannelType> executeableIn;
    private String description;
    private String usage;
    private String pattern;

    private Trigger trigger;

    private List<Argument<?>> arguments;


    private final static SectionValidator commandStructure = new SectionValidator()
            .addEntry("usage", true)
            .addEntry("description", true)
            .addEntry("roles", true)
            .addEntry("aliases", true)
            .addEntry("prefixes", false)
            .addEntry("executable in", true)
            .addSection("trigger", false);

    private static final Pattern commandPattern = Pattern.compile("(?i)^(on )?discord command (\\S+)(\\s+(.+))?$");
    private static final Pattern argumentPattern = Pattern.compile("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*(" + SkriptParser.wildcard + "))?\\s*>");
    private final static Pattern escape = Pattern.compile("[" + Pattern.quote("(|)<>%\\") + "]");
    private static final String listPattern = "\\s*,\\s*|\\s+(and|or|, )\\s+";

    public DiscordCommand(File script, String name, String pattern, List<Argument<?>> arguments, String[] prefixes,
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

    public static DiscordCommand add(SectionNode node) {
        if (!node.validate(commandStructure))
            return null;

        String command = node.getKey();
        if (command == null) return null;

        command = ScriptLoader.replaceOptions(command);
        Matcher matcher = commandPattern.matcher(command);
        if (!matcher.matches()) return null;

        command = matcher.group(2);
        DiscordCommand existingCommand = DiscordCommands.commandMap.get(command);
        if (existingCommand != null) {
            File script = existingCommand.getScript();
            Skript.error("A command with the name /" + existingCommand.getName() + " is already defined" + (script == null ? "" : " in " + script.getName()));
        }

        String arguments = matcher.group(4);
        final StringBuilder pattern = new StringBuilder();

        List<Argument<?>> currentArguments = Commands.currentArguments = new ArrayList<>();
        Matcher m = argumentPattern.matcher(arguments);
        int lastEnd = 0;
        int optionals = 0;

        for (int i = 0; m.find(); i++) {
            pattern.append(escape("" + arguments.substring(lastEnd, m.start())));
            optionals += StringUtils.count(arguments, '[', lastEnd, m.start());
            optionals -= StringUtils.count(arguments, ']', lastEnd, m.start());

            lastEnd = m.end();

            ClassInfo<?> c;
            c = Classes.getClassInfoFromUserInput("" + m.group(2));
            final NonNullPair<String, Boolean> p = Utils.getEnglishPlural("" + m.group(2));
            if (c == null)
                c = Classes.getClassInfoFromUserInput(p.getFirst());
            if (c == null) {
                Skript.error("Unknown type '" + m.group(2) + "'");
                return null;
            }
            final Parser<?> parser = c.getParser();
            if (parser == null || !parser.canParse(ParseContext.COMMAND)) {
                Skript.error("Can't use " + c + " as argument of a command");
                return null;
            }

            final Argument<?> arg = Argument.newInstance(m.group(1), c, m.group(3), i, !p.getSecond(), optionals > 0);
            if (arg == null)
                return null;
            currentArguments.add(arg);

            if (arg.isOptional() && optionals == 0) {
                pattern.append('[');
                optionals++;
            }
            pattern.append("%" + (arg.isOptional() ? "-" : "") + Utils.toEnglishPlural(c.getCodeName(), p.getSecond()) + "%");
        }

        pattern.append(escape("" + arguments.substring(lastEnd)));
        optionals += StringUtils.count(arguments, '[', lastEnd);
        optionals -= StringUtils.count(arguments, ']', lastEnd);
        for (int i = 0; i < optionals; i++)
            pattern.append(']');

        if (!(node.get("trigger") instanceof SectionNode))
            return null;

        SectionNode trigger = (SectionNode) node.get("trigger");
        String usage = ScriptLoader.replaceOptions(node.get("usage", ""));
        String description = ScriptLoader.replaceOptions(node.get("description", ""));
        String[] aliases = ScriptLoader.replaceOptions(node.get("aliases", "")).split(listPattern);
        String[] prefixes = ScriptLoader.replaceOptions(node.get("prefixes", "")).split(listPattern);
        String[] roles = ScriptLoader.replaceOptions(node.get("roles", "")).split(listPattern);
        String[] places = ScriptLoader.replaceOptions(node.get("executable in", "")).split(listPattern);
        List<ChannelType> channelTypes = new ArrayList<>();
        for (String place : places) {
            if (Util.equalsAnyIgnoreCase(place, "dm", "pm", "direct message", "private message")) {
                channelTypes.add(ChannelType.PRIVATE);
            } else if (Util.equalsAnyIgnoreCase(place, "guild", "server")) {
                channelTypes.add(ChannelType.TEXT);
            }
        }

        DiscordCommand discordCommand = new DiscordCommand(
                node.getConfig().getFile(), command, pattern.toString(), currentArguments,
                prefixes, Arrays.asList(aliases), description, usage, Arrays.asList(roles), channelTypes, ScriptLoader.loadItems(trigger)
        );

        DiscordCommands.commandMap.put(command, discordCommand);

        return discordCommand;

    }

    public boolean execute(final String commandLabel, final String rest) {
        //trigger.execute(new DiscordCommandEvent());
        return true;
    }

    private static String escape(final String s) {
        return "" + escape.matcher(s).replaceAll("\\\\$0");
    }

    public List<Argument<?>> getArguments() {
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
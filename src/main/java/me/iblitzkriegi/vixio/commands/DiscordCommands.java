package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.command.Argument;
import ch.njol.skript.command.Commands;
import ch.njol.skript.command.ScriptCommand;
import ch.njol.skript.command.ScriptCommandEvent;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Utils;
import ch.njol.skript.variables.Variables;
import ch.njol.util.NonNullPair;
import ch.njol.util.StringUtils;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.ChannelType;
import org.bukkit.event.Event;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscordCommands {

    public static Map<String, DiscordCommand> commandMap = new HashMap<>();
    private static final Method PARSE_I;

    static {

        Method _PARSE_I = null;
        try {
            _PARSE_I = SkriptParser.class.getDeclaredMethod("parse_i", String.class, int.class, int.class);
            _PARSE_I.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Skript.error("Skript's 'parse_i' method could not be resolved.");
        }
        PARSE_I = _PARSE_I;

    }

    private static final Pattern commandPattern = Pattern.compile("(?i)^(on )?discord command (\\S+)(\\s+(.+))?$");
    private static final Pattern argumentPattern = Pattern.compile("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*(" + SkriptParser.wildcard + "))?\\s*>");
    private final static Pattern escape = Pattern.compile("[" + Pattern.quote("(|)<>%\\") + "]");
    private static final String listPattern = "\\s*,\\s*|\\s+(and|or|, )\\s+";

    private final static SectionValidator commandStructure = new SectionValidator()
            .addEntry("usage", true)
            .addEntry("description", true)
            .addEntry("roles", true)
            .addEntry("aliases", true)
            .addEntry("prefixes", false)
            .addEntry("executable in", true)
            .addSection("trigger", false);

    private static String escape(final String s) {
        return "" + escape.matcher(s).replaceAll("\\\\$0");
    }

    public static boolean parseArguments(String args, DiscordCommand command, Event event) {
        SkriptParser parser = new SkriptParser(args, SkriptParser.PARSE_LITERALS, ParseContext.COMMAND);
        SkriptParser.ParseResult res = null;
        try {
            res = (SkriptParser.ParseResult) PARSE_I.invoke(parser, command.getPattern(), 0, 0);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (res == null)
            return false;

        List<DiscordArgument<?>> as = command.getArguments();
        assert as.size() == res.exprs.length;
        for (int i = 0; i < res.exprs.length; i++) {
            if (res.exprs[i] == null)
                as.get(i).setToDefault(event);
            else
                as.get(i).set(event, res.exprs[i].getArray(event));
        }
        return true;
    }

    public static DiscordCommand add(SectionNode node) {

        String command = node.getKey();
        if (command == null) return null;

        command = ScriptLoader.replaceOptions(command);
        Matcher matcher = commandPattern.matcher(command);
        if (!matcher.matches()) return null;

        command = matcher.group(2);
        DiscordCommand existingCommand = DiscordCommands.commandMap.get(command);
        if (existingCommand != null) {
            File script = existingCommand.getScript();
            Skript.error("A discord command with the name \"" + existingCommand.getName() + "\" is already defined" + (script == null ? "" : " in " + script.getName()));
        }

        String arguments = matcher.group(4);
        final StringBuilder pattern = new StringBuilder();

        List<DiscordArgument<?>> currentArguments = new ArrayList<>();
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

            final DiscordArgument<?> arg = DiscordArgument.newInstance(m.group(1), c, m.group(3), i, !p.getSecond(), optionals > 0);
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

        node.convertToEntries(0);
        if (!commandStructure.validate(node))
            return null;

        if (!(node.get("trigger") instanceof SectionNode))
            return null;

        SectionNode trigger = (SectionNode) node.get("trigger");
        ArrayList<TriggerItem> loadedTrigger = ScriptLoader.loadItems(trigger);
        if (loadedTrigger.isEmpty())
            return null;
        String usage = ScriptLoader.replaceOptions(node.get("usage", ""));
        String description = ScriptLoader.replaceOptions(node.get("description", ""));
        String[] aliases = ScriptLoader.replaceOptions(node.get("aliases", "")).split(listPattern);
        String[] prefixes = ScriptLoader.replaceOptions(node.get("prefixes", "")).split(listPattern);
        for (String s : prefixes) System.out.println(s);
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

    public static boolean remove(String name) {
        return commandMap.remove(name) != null;
    }

}

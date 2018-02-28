package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.RetainingLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Utils;
import ch.njol.util.NonNullPair;
import ch.njol.util.StringUtils;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import net.dv8tion.jda.core.entities.ChannelType;
import org.bukkit.event.Event;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscordCommandFactory {

    private static final DiscordCommandFactory INSTANCE = new DiscordCommandFactory();
    private final Method PARSE_I;
    private final Pattern commandPattern = Pattern.compile("(?i)^(on )?discord command (\\S+)(\\s+(.+))?$");
    private final Pattern argumentPattern = Pattern.compile("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*(" + SkriptParser.wildcard + "))?\\s*>");
    private final Pattern escape = Pattern.compile("[" + Pattern.quote("(|)<>%\\") + "]");
    private final String listPattern = "\\s*,\\s*|\\s+(and|or|, )\\s+";

    private final SectionValidator commandStructure = new SectionValidator()
            .addEntry("usage", true)
            .addEntry("description", true)
            .addEntry("roles", true)
            .addEntry("aliases", true)
            .addEntry("prefixes", false)
            .addEntry("bots", true)
            .addEntry("executable in", true)
            .addSection("trigger", false);

    public HashMap<String, DiscordCommand> commandMap = new HashMap<>();
    public List<DiscordArgument<?>> currentArguments;

    private DiscordCommandFactory() {

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

    public static DiscordCommandFactory getInstance() {
        return INSTANCE;
    }

    private String escape(final String s) {
        return "" + escape.matcher(s).replaceAll("\\\\$0");
    }

    public boolean parseArguments(String args, DiscordCommand command, Event event) {
        SkriptParser parser = new SkriptParser(args, SkriptParser.PARSE_LITERALS, ParseContext.COMMAND);
        SkriptParser.ParseResult res = null;
        try {
            res = (SkriptParser.ParseResult) PARSE_I.invoke(parser, command.getPattern(), 0, 0);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (res == null) {
            return false;
        }

        List<DiscordArgument<?>> as = command.getArguments();
        assert as.size() == res.exprs.length;
        for (int i = 0; i < res.exprs.length; i++) {
            if (res.exprs[i] == null) {
                as.get(i).setToDefault(event);
            } else {
                as.get(i).set(event, res.exprs[i].getArray(event));
            }
        }
        return true;
    }

    public ArrayList<ChannelType> parsePlaces(String[] places) {
        ArrayList<ChannelType> types = new ArrayList<ChannelType>();
        for (String place : places) {
            if (Util.equalsAnyIgnoreCase(place, "server", "guild")) {
                types.add(ChannelType.TEXT);
            } else if (Util.equalsAnyIgnoreCase(place, "dm", "pm", "direct message", "private message")) {
                types.add(ChannelType.PRIVATE);
            } else {
                Skript.error("'executable in' should be either 'guild', 'dm', or both, but found '" + place + "'");
                return null;
            }
        }
        return types;
    }

    public DiscordCommand add(SectionNode node) {

        String command = node.getKey();
        if (command == null) {
            return null;
        }

        command = ScriptLoader.replaceOptions(command);
        Matcher matcher = commandPattern.matcher(command);
        if (!matcher.matches()) {
            return null;
        }

        int level = 0;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == '[') {
                level++;
            } else if (command.charAt(i) == ']') {
                if (level == 0) {
                    Skript.error("Invalid placement of [optional brackets]");
                    return null;
                }
                level--;
            }
        }
        if (level > 0) {
            Skript.error("Invalid amount of [optional brackets]");
            return null;
        }

        command = matcher.group(2);
        DiscordCommand existingCommand = this.commandMap.get(command);
        if (existingCommand != null) {
            File script = existingCommand.getScript();
            Skript.error("A discord command with the name \"" + existingCommand.getName() + "\" is already defined" + (script == null ? "" : " in " + script.getName()));
        }

        String arguments = matcher.group(4);
        if (arguments == null) {
            arguments = "";
        }

        final StringBuilder pattern = new StringBuilder();

        List<DiscordArgument<?>> currentArguments = this.currentArguments = new ArrayList<>();
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
        if (!commandStructure.validate(node)) {
            return null;
        }

        if (!(node.get("trigger") instanceof SectionNode)) {
            return null;
        }

        SectionNode trigger = (SectionNode) node.get("trigger");

        String usage = ScriptLoader.replaceOptions(node.get("usage", ""));

        String description = ScriptLoader.replaceOptions(node.get("description", ""));

        String aliasesString = ScriptLoader.replaceOptions(node.get("aliases", ""));
        List<String> aliases = aliasesString.isEmpty() ? null : Arrays.asList(aliasesString.split(listPattern));

        String[] prefixes = ScriptLoader.replaceOptions(node.get("prefixes", "")).split(listPattern);

        String roleString = ScriptLoader.replaceOptions(node.get("roles", ""));
        List<String> roles = roleString.isEmpty() ? null : Arrays.asList(roleString.split(listPattern));

        String botString = ScriptLoader.replaceOptions(node.get("bots", ""));
        List<String> bots = botString.isEmpty() ? null : Arrays.asList(botString.split(listPattern));

        List<ChannelType> places = parsePlaces(ScriptLoader.replaceOptions(node.get("executable in", "guild, dm")).split(listPattern));

        if (places == null) {
            return null;
        }

        RetainingLogHandler errors = SkriptLogger.startRetainingLog();
        DiscordCommand discordCommand;
        this.currentArguments = currentArguments;
        try {
            discordCommand = new DiscordCommand(
                    node.getConfig().getFile(), command, pattern.toString(), currentArguments,
                    Arrays.asList(prefixes), aliases, description, usage, roles, places, bots, ScriptLoader.loadItems(trigger)
            );
        } finally {
            this.currentArguments = null;
            EffectSection.stopLog(errors);
        }

        this.commandMap.put(command, discordCommand);

        currentArguments = null;
        return discordCommand;

    }

    public boolean remove(String name) {
        return commandMap.remove(name) != null;
    }

    public Collection<DiscordCommand> getCommands() {
        return commandMap.values();
    }
}

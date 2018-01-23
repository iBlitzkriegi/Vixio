/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
package me.iblitzkriegi.vixio.commands;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

    public static Map<String, DiscordCommand> commandMap = new HashMap<>();

    final String name;
    private final String label;
    private final List<String> aliases;
    private List<String> activeAliases;
    private final String description;
    final String usage;

    final Trigger trigger;

    private final List<Argument<?>> arguments;

    private final static SectionValidator commandStructure = new SectionValidator()
            .addEntry("usage", true)
            .addEntry("description", true)
            .addEntry("roles", true)
            .addEntry("aliases", true)
            .addEntry("prefix", false)
            .addEntry("executable in", true)
            .addSection("trigger", false);

    private static final Pattern commandPattern = Pattern.compile("(?i)^(on )?discord command (\\S+)(\\s+(.+))?$");
    private static final Pattern argumentPattern = Pattern.compile("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*(" + SkriptParser.wildcard + "))?\\s*>");
    private final static Pattern escape = Pattern.compile("[" + Pattern.quote("(|)<>%\\") + "]");

    private final static String escape(final String s) {
        return "" + escape.matcher(s).replaceAll("\\\\$0");
    }

    public static DiscordCommand add(SectionNode node) {
        String command = node.getKey();
        if (command == null) return null;

        command = ScriptLoader.replaceOptions(command);
        Matcher matcher = commandPattern.matcher(command);
        if (!matcher.matches()) return null;

        command = matcher.group(2);
        DiscordCommand existingCommand = commandMap.get(command);
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



        return new DiscordCommand(
                node.getConfig().getFile(), command, pattern, currentArguments,
                prefixes, aliases, description, usage, roles
        );
    }

    public DiscordCommand(final File script, final String name, final String pattern, List<Argument<?>> arguments, ArrayList<String> prefixes,
                          ArrayList<String> aliases, String description, String usage, List<String> roles,
                              ChannelType executableIn, List<TriggerItem> items) {
            Validate.notNull(name, pattern, arguments, description, usage, aliases, items);
            this.name = name;
            label = "" + name.toLowerCase();

            aliases.removeIf(alias -> alias.equalsIgnoreCase(label));
            this.aliases = aliases;
            activeAliases = new ArrayList<String>(aliases);

            this.description = Utils.replaceEnglishChatStyles(description);
            this.usage = Utils.replaceEnglishChatStyles(usage);

            this.arguments = arguments;

            trigger = new Trigger(script, "discord command " + name, new SimpleEvent(), items);

    }

    public boolean execute(final String commandLabel, final String rest) {
        return true;
    }


    public List<Argument<?>> getArguments() {
        return arguments;
    }


    public void register() {

    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public List<String> getActiveAliases() {
        return activeAliases;
    }

    @Nullable
    public File getScript() {
        return trigger.getScript();
    }

}
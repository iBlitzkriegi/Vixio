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
import ch.njol.skript.command.Argument;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
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
    private static final Pattern argumentPattern = Pattern.compile("");

    public static DiscordCommand add(SectionNode node) {
        String command = node.getKey();
        if (command == null) return null;

        command = ScriptLoader.replaceOptions(command);
        Matcher matcher = commandPattern.matcher(command);
        if (!matcher.matches()) return null;

        command = matcher.group(2);
        String stringArgs = matcher.group(4);

        if (stringArgs != null) {
            for (String stringArg : stringArgs.split("")) {
                System.out.println("Arg is " + stringArg);
            }
        }
        return null;
    }

    public DiscordCommand(final File script, final String name, final String pattern, final List<Argument<?>> arguments, final String description, final String usage, final ArrayList<String> aliases, final String permission, final String permissionMessage, final int executableBy, final List<TriggerItem> items) {
        Validate.notNull(name, pattern, arguments, description, usage, aliases, items);
        this.name = name;
        label = "" + name.toLowerCase();

        final Iterator<String> as = aliases.iterator();
        while (as.hasNext()) { // remove aliases that are the same as the command
            if (as.next().equalsIgnoreCase(label))
                as.remove();
        }
        this.aliases = aliases;
        activeAliases = new ArrayList<String>(aliases);

        this.description = Utils.replaceEnglishChatStyles(description);
        this.usage = Utils.replaceEnglishChatStyles(usage);

        this.arguments = arguments;

        trigger = new Trigger(script, "command /" + name, new SimpleEvent(), items);

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
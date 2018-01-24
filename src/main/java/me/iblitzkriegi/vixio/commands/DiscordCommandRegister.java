package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.config.Config;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.log.SkriptLogger;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;

public class DiscordCommandRegister extends SelfRegisteringSkriptEvent {

    private String arguments;
    private String command;

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandRegister.class, null, "discord command <([^\\s]+)( .+)?$>");
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        command = parser.regexes.get(0).group(1);
        arguments = parser.regexes.get(0).group(2);
        SectionNode sectionNode = (SectionNode) SkriptLogger.getNode();
        DiscordCommand cmd = DiscordCommands.add(sectionNode);
        Util.nukeSectionNode(sectionNode);
        return cmd != null;
    }

    @Override
    public void afterParse(Config config) {}

    @Override
    public void register(final Trigger t) {}

    @Override
    public void unregister(final Trigger t) {
        DiscordCommands.remove(command);
    }

    @Override
    public void unregisterAll() {}

    @Override
    public String toString(final Event e, final boolean debug) {
        return "discord command " + command + (arguments == null ? "" : arguments);
    }

}
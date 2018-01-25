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
    public static boolean commandParsing = false;

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandRegister.class, DiscordCommandEvent.class, "discord command <([^\\s]+)( .+)?$>");
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        command = parser.regexes.get(0).group(1);
        arguments = parser.regexes.get(0).group(2);
        SectionNode sectionNode = (SectionNode) SkriptLogger.getNode();
        commandParsing = true;
        DiscordCommand cmd = DiscordCommands.getInstance().add(sectionNode);
        commandParsing = false;
        Util.nukeSectionNode(sectionNode);
        return cmd != null;
    }

    @Override
    public void afterParse(Config config) {
        commandParsing = false;
    }

    @Override
    public void register(final Trigger t) {}

    @Override
    public void unregister(final Trigger t) {
        DiscordCommands.getInstance().remove(command);
    }

    @Override
    public void unregisterAll() {}

    @Override
    public String toString(final Event e, final boolean debug) {
        return "discord command " + command + (arguments == null ? "" : arguments);
    }

}
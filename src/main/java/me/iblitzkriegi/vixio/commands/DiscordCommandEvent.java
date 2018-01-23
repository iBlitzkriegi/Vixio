package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.config.Config;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.log.RetainingLogHandler;
import ch.njol.skript.log.SkriptLogger;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.events.bukkit.ScriptEvent;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;

/**
 * @author Peter Güttinger
 */
public class DiscordCommandEvent extends SelfRegisteringSkriptEvent {

    private SectionNode sectionNode;
    private String command;

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandEvent.class, null, "discord command <.+>");
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        command = parser.regexes.get(0).group();
        SectionNode originalSectionNode = (SectionNode) SkriptLogger.getNode();
        sectionNode = new SectionNode(originalSectionNode.getKey(), "", originalSectionNode.getParent(), originalSectionNode.getLine());
        Util.nukeSectionNode(originalSectionNode);
        return DiscordCommand.add(sectionNode) != null;
    }

    @Override
    public void afterParse(Config config) {}

    @Override
    public void register(final Trigger t) {}

    @Override
    public void unregister(final Trigger t) {}

    @Override
    public void unregisterAll() {}

    @Override
    public String toString(final Event e, final boolean debug) {
        return "discord command " + command;
    }

}
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

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandEvent.class, null, "discord command <.+>");
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        SectionNode sectionNode = (SectionNode) SkriptLogger.getNode();
        DiscordCommand.add(sectionNode);
        Util.nukeSectionNode(sectionNode);
        return true;
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
    public String toString(final @Nullable Event e, final boolean debug) {
        return "discord command";
    }

}
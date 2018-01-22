package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.log.SkriptLogger;
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
    static {
        Skript.registerEvent("Script Load/Unload", DiscordCommandEvent.class, ScriptEvent.class, "discord command <.*>");
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        System.out.println("Node is " + SkriptLogger.getNode());
        return true;
    }

    @Override
    public void register(final Trigger t) {
    }

    @Override
    public void unregister(final Trigger t) {
    }

    @Override
    public void unregisterAll() {
    }

    @Override
    public String toString(final @Nullable Event e, final boolean debug) {
        return "discord command";
    }

}
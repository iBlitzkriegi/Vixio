package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.config.Config;
import ch.njol.skript.log.RetainingLogHandler;
import ch.njol.skript.log.SkriptLogger;
import me.iblitzkriegi.vixio.Vixio;
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

    private RetainingLogHandler handler;

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandEvent.class, null, "discord command <.*>");
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        System.out.println("Node is " + SkriptLogger.getNode());
        handler = SkriptLogger.startRetainingLog();
        System.out.println("Started log " + handler);
        return true;
    }

    @Override
    public void afterParse(Config config) {
        System.out.println("stopping " + handler);
        handler.clear();
        handler.printErrors();
        handler.stop();
        System.out.println("stopped " + handler);
    }

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
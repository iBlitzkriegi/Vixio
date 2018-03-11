package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommandEvent;
import org.bukkit.event.Event;

public class EvtDiscordCommand extends SkriptEvent {

    static {

        Vixio.getInstance().registerEvent("vixio command event", EvtDiscordCommand.class, DiscordCommandEvent.class,
                "vixio command [%-string%]");

    }

    private String command;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        command = args[0] == null ? null : ((Literal<String>) args[0]).getSingle();
        return true;
    }

    @Override
    public boolean check(Event e) {
        return command == null || command.equals(((DiscordCommandEvent) e).getCommand().getName());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "discord command" + (command == null ? "" : " " + command);
    }

}

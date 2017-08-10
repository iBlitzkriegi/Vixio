package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.*;
import me.iblitzkriegi.vixio.Vixio;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/6/2017.
 */
public class EventCustomCommand extends SelfRegisteringSkriptEvent {
    static {
        Vixio.registerEvent("CustomCommandEvent", SkriptEvent.class, EventCustomCommand.class, "discord command[s] %strings% [in server] [from bot %string%]")
            .setName("Discord command")
            .setDesc("Listen for specific discord commands for your bot")
            .setExample("soon");
    }
    @Override
    public void register(Trigger trigger) {
        System.out.println("In the register method");
    }

    @Override
    public void unregister(Trigger trigger) {
        System.out.println("In the unregister method");
    }

    @Override
    public void unregisterAll() {
        System.out.println("In the unregister all method");
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        System.out.println("Here now!");
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }
}

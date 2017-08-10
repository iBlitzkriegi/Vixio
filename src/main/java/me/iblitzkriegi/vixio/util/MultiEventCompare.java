package me.iblitzkriegi.vixio.util;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.iblitzkriegi.vixio.events.EventJDAEvent;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/28/2017.
 */
public class MultiEventCompare extends SkriptEvent{
    private Expression<String> event;
    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        event = (Expression<String>) literals[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        if(event.getSingle(e) != null) {
            if (e instanceof EventJDAEvent) {
                if (((EventJDAEvent) e).getEvent().getClass().getSimpleName().equalsIgnoreCase(event.getSingle(e))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }
}

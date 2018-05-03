package me.iblitzkriegi.vixio.util.skript;


import ch.njol.skript.Skript;
import ch.njol.skript.events.bukkit.SkriptStopEvent;
import ch.njol.skript.lang.TriggerItem;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/**
 * Effects that extend this class are ran asynchronously. Next trigger item will
 * be ran in main server thread, as if there had been a delay before.
 * <p>
 * Majority of Skript and Minecraft APIs are not thread-safe, so be careful.
 */
public abstract class AsyncEffect extends DelayFork {

    @Override
    @Nullable
    protected TriggerItem walk(Event e) {
        debug(e, true);
        TriggerItem next = getNext();
        // if (e.getEventName().equals("SkriptStopEvent")) {
        if (e.getClass().isAssignableFrom(SkriptStopEvent.class)) {    // Because a bukkit task can't be created on server stop
            execute(e);
            if (next != null)
                TriggerItem.walk(next, e);
        } else {
            DelayFork.addDelayedEvent(e);
            Bukkit.getScheduler().runTaskAsynchronously(Skript.getInstance(), new Runnable() {
                // @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    execute(e); // Execute this effect
                    if (next != null) {
                        Bukkit.getScheduler().runTask(Skript.getInstance(), new Runnable() {
                            @Override
                            public void run() { // Walk to next item synchronously
                                // walk(next, e);
                                TriggerItem.walk(next, e);

                            }
                        });
                    }
                }
            });
        }
        return null;
    }
}
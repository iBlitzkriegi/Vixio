package me.iblitzkriegi.vixio.util.skript;


import ch.njol.skript.Skript;
import ch.njol.skript.events.bukkit.SkriptStopEvent;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.variables.Variables;
import me.iblitzkriegi.vixio.Vixio;
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
    protected TriggerItem walk(final Event event) {

        Object varMap = null;
        /* Check if we managing locale variable or not */
        if (!Vixio.useOldSkript) {
            varMap = Variables.removeLocals(event);
            Variables.setLocalVariables(event, map);
        }

        debug(event, true);
        TriggerItem next = getNext();
        // if (e.getEventName().equals("SkriptStopEvent")) {
        if (event.getClass().isAssignableFrom(SkriptStopEvent.class)) {    // Because a bukkit task can't be created on server stop
            execute(event);
            if (next != null)
                TriggerItem.walk(next, event);
        } else {
            DelayFork.addDelayedEvent(event);
            Object finalVarMap = varMap;
            Bukkit.getScheduler().runTaskAsynchronously(Skript.getInstance(), new Runnable() {
                // @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    execute(event); // Execute this effect
                    if (next != null) {
                        Bukkit.getScheduler().runTask(Skript.getInstance(), new Runnable() {
                            @Override
                            public void run() { // Walk to next item synchronously
                                // walk(next, e);

                                /* And here we re-set all previous locale variables */
                                if (!Vixio.useOldSkript)
                                    if (finalVarMap != null)
                                        Variables.setLocalVariables(event, finalVarMap);

                                TriggerItem.walk(next, event);

                                /* Also remove the current locales variables of that event, else Skript will be confused with both map! */
                                if (!Vixio.useOldSkript)
                                    Variables.removeLocals(event);
                            }
                        });
                    }
                }
            });
        }
        return null;
    }
}
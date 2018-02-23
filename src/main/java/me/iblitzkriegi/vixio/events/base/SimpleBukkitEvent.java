package me.iblitzkriegi.vixio.events.base;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SimpleBukkitEvent extends Event {

    private HandlerList handlerList = new HandlerList();

    public HandlerList getHandlers() {
        return handlerList;
    }

}

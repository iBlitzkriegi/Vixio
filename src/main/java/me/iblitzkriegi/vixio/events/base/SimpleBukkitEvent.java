package me.iblitzkriegi.vixio.events.base;

import org.bukkit.event.HandlerList;

public class SimpleBukkitEvent extends org.bukkit.event.Event {

    private static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}

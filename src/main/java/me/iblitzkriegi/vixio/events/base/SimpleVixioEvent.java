package me.iblitzkriegi.vixio.events.base;

import me.iblitzkriegi.vixio.Vixio;

public class SimpleVixioEvent<T extends net.dv8tion.jda.core.events.Event> extends SimpleBukkitEvent {

    private T JDAEvent;

    public T getJDAEvent() {
        return JDAEvent;
    }

    public void setJDAEvent(T JDAEvent) {
        this.JDAEvent = JDAEvent;
    }

}

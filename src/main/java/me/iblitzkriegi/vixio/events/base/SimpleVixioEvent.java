package me.iblitzkriegi.vixio.events.base;

public class SimpleVixioEvent<D extends net.dv8tion.jda.core.events.Event> extends SimpleBukkitEvent {

    private D JDAEvent;

    public D getJDAEvent() {
        return JDAEvent;
    }

    public void setJDAEvent(D JDAEvent) {
        this.JDAEvent = JDAEvent;
    }

}

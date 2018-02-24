package me.iblitzkriegi.vixio.events.base;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventListener<T> extends ListenerAdapter {

    public static ArrayList<EventListener<?>> listeners = new ArrayList<>();

    private Class<T> clazz;

    public EventListener(Class<T> paramClass, Consumer<T> consumer) {
        this.clazz = paramClass;
        this.consumer = consumer;
    }

    private Consumer<T> consumer;
    private boolean enabled = true;

    public void setEnabled(boolean enabled) {
        if (enabled && !this.enabled) {
            this.enabled = enabled;
            listeners.add(this);
        } else if (!enabled && this.enabled) {
            this.enabled = enabled;
            listeners.remove(this);
        }
    }

    @Override
    public void onGenericEvent(net.dv8tion.jda.core.events.Event event) {
        if (enabled && clazz.isInstance(event)) {
            consumer.accept((T) event);
        }
    }

}

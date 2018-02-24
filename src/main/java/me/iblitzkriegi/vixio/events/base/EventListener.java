package me.iblitzkriegi.vixio.events.base;

import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventListener<T> extends ListenerAdapter {

    // shouldn't be modified directly
    public static ArrayList<EventListener<?>> listeners = new ArrayList<>();

    public boolean enabled = true;
    private Class<T> clazz;
    private Consumer<T> consumer;

    public EventListener(Class<T> paramClass, Consumer<T> consumer) {
        this.clazz = paramClass;
        this.consumer = consumer;
    }

    public static void addListener(EventListener<?> listener) {
        removeListener(listener);
        listeners.add(listener);
        Vixio.getInstance().botHashMap.forEach((k, v) -> v.getJDA().addEventListener(listener));
    }

    public static void removeListener(EventListener<?> listener) {
        listeners.remove(listener);
        Vixio.getInstance().botHashMap.forEach((k, v) -> v.getJDA().removeEventListener(listener));
    }

    @Override
    public void onGenericEvent(net.dv8tion.jda.core.events.Event event) {
        if (enabled && clazz.isInstance(event)) {
            consumer.accept((T) event);
        }
    }

}

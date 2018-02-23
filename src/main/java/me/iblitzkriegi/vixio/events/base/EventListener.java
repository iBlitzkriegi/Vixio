package me.iblitzkriegi.vixio.events.base;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.util.function.Consumer;

public class EventListener<T> extends ListenerAdapter {

    private Class<T> clazz;

    public EventListener(Class<T> paramClass, Consumer<T> consumer) {
        this.clazz = paramClass;
        this.consumer = consumer;
    }

    private Consumer<T> consumer;
    public boolean enabled = true;

    @Override
    public void onGenericEvent(net.dv8tion.jda.core.events.Event event) {
        if (enabled && clazz.isInstance(event)) {
            consumer.accept((T) event);
        }
    }

}

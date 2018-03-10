package me.iblitzkriegi.vixio.events.base;

import java.util.HashMap;
import java.util.Map;

public class SimpleVixioEvent<D extends net.dv8tion.jda.core.events.Event> extends SimpleBukkitEvent {

    private D JDAEvent;
    private Map<Class<?>, Object> valueMap = new HashMap<>();

    public D getJDAEvent() {
        return JDAEvent;
    }

    public void setJDAEvent(D JDAEvent) {
        this.JDAEvent = JDAEvent;
    }

    public Map<Class<?>, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Class<?>, Object> valueMap) {
        this.valueMap = valueMap;
    }

    public <T> T getValue(Class<T> clazz) {
        return (T) valueMap.get(clazz);
    }

}

package me.iblitzkriegi.vixio.events.base;

import java.util.HashMap;

public class SimpleVixioEvent<D extends net.dv8tion.jda.core.events.Event> extends SimpleBukkitEvent {

    private D JDAEvent;
    private HashMap<Class<?>, Object> valueMap = new HashMap<>();

    public D getJDAEvent() {
        return JDAEvent;
    }

    public void setJDAEvent(D JDAEvent) {
        this.JDAEvent = JDAEvent;
    }

    public HashMap<Class<?>, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(HashMap<Class<?>, Object> valueMap) {
        this.valueMap = valueMap;
    }

    public <T> T getValue(Class<T> clazz) {
        return (T) valueMap.get(clazz);
    }

}

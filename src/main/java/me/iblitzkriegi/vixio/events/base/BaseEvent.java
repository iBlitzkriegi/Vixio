package me.iblitzkriegi.vixio.events.base;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.config.Config;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptEventInfo;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.log.SkriptLogger;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.util.Util;
import org.bukkit.event.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public abstract class BaseEvent<D extends net.dv8tion.jda.core.events.Event> extends SelfRegisteringSkriptEvent {

    /**
     * The ending appended to patterns if no custom ending is specified
     */
    public static final String APPENDED_ENDING = "[seen by %-string%]";
    private String stringRepresentation;
    private Trigger trigger;
    private EventListener<D> listener;
    private String bot;
    private Class<? extends org.bukkit.event.Event> bukkitClass;
    private Class<D> jdaClass;
    private String originalName;
    private Class<? extends Event>[] originalEvents;
    private Constructor<?> constructor;

    /**
     * @param name     The name of the event used for ScriptLoader#setCurrentEvents
     * @param type     The class holding the event
     * @param clazz    The class holding the Bukkit event
     * @param patterns The patterns for the event
     */
    public static Registration register(String name, Class type, Class clazz, String... patterns) {
        return register(name, APPENDED_ENDING, type, clazz, patterns);
    }

    /**
     * /**
     *
     * @param name     The name of the event used for ScriptLoader#setCurrentEvents
     * @param ending   The ending applied for checking the bot (which can be grabbed via BaseEvent.APPENDED_ENDING)
     * @param type     The class holding the event
     * @param clazz    The class holding the Bukkit event
     * @param patterns The patterns for the event
     */
    public static Registration register(String name, String ending, Class type, Class clazz, String... patterns) {
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] += " " + ending;
        }
        return Vixio.getInstance().registerEvent(name, type, clazz, patterns);
    }

    @Override
    public boolean init(Literal<?>[] exprs, int matchedPattern, SkriptParser.ParseResult parser) {
        bot = (String) (exprs[0] == null ? null : exprs[0].getSingle());

        bukkitClass = (Class<? extends org.bukkit.event.Event>) Arrays.stream(this.getClass().getDeclaredClasses())
                .filter(innerClass -> innerClass.getSuperclass() == SimpleVixioEvent.class)
                .findFirst()
                .orElse(null);

        if (bukkitClass == null) {
            throw new RuntimeException(this.getClass().getCanonicalName() + " doesn't have an inner SimpleVixioEvent " +
                    "class to be instantiated. Report this at https://github.com/iblitzkriegi/vixio/issues!");
        }

        try {
            jdaClass = (Class<D>) ((ParameterizedType) bukkitClass.getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            throw new RuntimeException(this.getClass().getCanonicalName() + "'s inner class doesn't use the same JDA" +
                    " event as it's parent class in it's SimpleVixioEvent. Report this at https://github.com/iblitzkriegi/vixio/issues!");
        }

        try {
            constructor = bukkitClass.getDeclaredConstructor(this.getClass());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        stringRepresentation = ScriptLoader.replaceOptions(SkriptLogger.getNode().getKey()) + ":";
        originalName = ScriptLoader.getCurrentEventName();
        originalEvents = ScriptLoader.getCurrentEvents();

        String name = null;
        for (SkriptEventInfo<?> event : Skript.getEvents()) {
            if (bukkitClass.equals(event.c)) {
                name = event.getName();
            }
        }

        ScriptLoader.setCurrentEvent(name == null ? "Vixio event" : name, bukkitClass);
        return true;
    }

    @Override
    public void afterParse(Config config) {
        ScriptLoader.setCurrentEvent(originalName, originalEvents);
    }

    @Override
    public void register(Trigger t) {
        trigger = t;
        listener = new EventListener<>(jdaClass, JDAEvent -> {
            if (check(JDAEvent)) {

                /* !? */
                final SimpleVixioEvent<D> event;
                SimpleVixioEvent<D> compilerWorkaround = null;
                try {
                    compilerWorkaround = (SimpleVixioEvent<D>) constructor.newInstance(BaseEvent.this);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                event = compilerWorkaround;

                event.setJDAEvent(JDAEvent);
                Util.sync(() -> getTrigger().execute(event));

            }
        });
        EventListener.addListener(listener);
    }

    @Override
    public void unregister(final Trigger t) {
        listener.enabled = false;
        EventListener.removeListener(listener);
        listener = null;
        trigger = null;
    }

    @Override
    public void unregisterAll() {
        unregister(trigger);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return stringRepresentation;
    }

    public String getBot() {
        return bot;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    /**
     * Used to check whether or not the event is valid for the trigger to run.
     *
     * @param event The JDA event to be checked
     **/
    public boolean check(D event) {
        return bot == null || bot.equalsIgnoreCase(Util.botFromID(event.getJDA().getSelfUser().getId()).getName());
    }

    public Class<? extends Event> getBukkitClass() {
        return bukkitClass;
    }

    public Class<D> getJDAClass() {
        return jdaClass;
    }

}
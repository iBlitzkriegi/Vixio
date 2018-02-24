package me.iblitzkriegi.vixio.events.base;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.config.Config;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptEventInfo;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.commands.DiscordCommandEvent;
import me.iblitzkriegi.vixio.commands.DiscordCommandFactory;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseEvent<D extends net.dv8tion.jda.core.events.Event, B extends org.bukkit.event.Event>
        extends SelfRegisteringSkriptEvent {

    public static void register(String name, Class type, Class clazz, String... patterns) {
        register(name, "[seen by %-string%]", type, clazz, patterns);
    }

    public static void register(String name, String ending, Class type, Class clazz, String... patterns) {
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] += " " + ending;
        }
        Vixio.getInstance().registerEvent(name, type, clazz, patterns);
    }

    private Trigger trigger;
    private EventListener<D> listener;
    private String bot;
    private String originalName;
    private Class<? extends Event>[] originalEvents;
    public String stringRepresentation;

    @Override
    public boolean init(Literal<?>[] exprs, int matchedPattern, SkriptParser.ParseResult parser) {
        bot = (String) (exprs[0] == null ? null : exprs[0].getSingle());

        stringRepresentation = ScriptLoader.replaceOptions(SkriptLogger.getNode().getKey()) + ":";
        originalName = ScriptLoader.getCurrentEventName();
        originalEvents = ScriptLoader.getCurrentEvents();

        String name = null;
        for (SkriptEventInfo<?> event : Skript.getEvents()) {
            if (getBukkitClass().equals(event.c)) {
                name = event.getName();
            }
        }

        ScriptLoader.setCurrentEvent(name == null ? "Vixio event" : name, getBukkitClass());
        return true;
    }

    @Override
    public void afterParse(Config config) {
        ScriptLoader.setCurrentEvent(originalName, originalEvents);
    }

    @Override
    public void register(final Trigger t) {
        trigger = t;
        listener = new EventListener<>(getJDAClass(), new Consumer<D>() {
            @Override
            public void accept(D d) {
                B event = getNewEvent(d);
                if (bot == null || bot.equalsIgnoreCase(Vixio.getInstance().botHashMap.get(d.getJDA()).getName())) {
                    getTrigger().execute(event);
                }
            }
        });
        Vixio.getInstance().botHashMap.forEach((k, v) -> v.getJDA().addEventListener(listener));
    }

    @Override
    public void unregister(final Trigger t) {
        listener.enabled = false;
        Vixio.getInstance().botHashMap.forEach((k, v) -> v.getJDA().removeEventListener(listener));
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

    public Trigger getTrigger() {
        return trigger;
    }

    public abstract B getNewEvent(D JDAEvent);

    public abstract Class<D> getJDAClass();

    public abstract Class<B> getBukkitClass();

}

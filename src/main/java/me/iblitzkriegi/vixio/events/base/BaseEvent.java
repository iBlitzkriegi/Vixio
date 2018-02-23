package me.iblitzkriegi.vixio.events.base;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.config.Config;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
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

    private Trigger trigger;
    private Class<D> clazz = getEventClass();
    private EventListener<D> listener;

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    public void afterParse(Config config) {
    }

    @Override
    public void register(final Trigger t) {
        this.trigger = t;
        listener = new EventListener<>(clazz, new Consumer<D>() {
            @Override
            public void accept(D event) {
                trigger.execute(getNewEvent(event));
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

    public abstract B getNewEvent(D JDAEvent);

    public abstract Class<D> getEventClass();

}

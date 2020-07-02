package me.iblitzkriegi.vixio.events.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.TrackEvent;
import org.bukkit.event.Event;

public class EvtTrackSeek extends SkriptEvent {
    static {
        BaseEvent.register("track start", EvtTrackSeek.class, TrackEvent.class, "track seek")
                .setName("Track Seek")
                .setDesc("Fired when the position of a track a bot is playing is changed. This is when it is manually changed, via set position.")
                .setExample("on track seek seen by \"Jewel\"");
    }

    private Expression<String> bot;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        bot = (Expression<String>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        return ((TrackEvent) e).getState() == TrackEvent.TrackState.SEEK;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "on track seek" + (bot == null ? "" : " seen by" + bot.toString(e, debug));
    }
}

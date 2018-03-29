package me.iblitzkriegi.vixio.events.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.TrackEvent;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class EvtTrackStart extends SkriptEvent {
    static {
        BaseEvent.register("track start", EvtTrackStart.class, TrackEvent.class, "track start")
                .setName("Track start")
                .setDesc("Fired when a song starts. May be when a new track in the queue starts or when the first track is played.")
                .setExample("on track start seen by \"Jewel\"");
        EventValues.registerEventValue(TrackEvent.class, Bot.class, new Getter<Bot, TrackEvent>() {
            @Override
            public Bot get(TrackEvent event) {
                return event.getBot();
            }
        }, 0);

        EventValues.registerEventValue(TrackEvent.class, Guild.class, new Getter<Guild, TrackEvent>() {
            @Override
            public Guild get(TrackEvent event) {
                return event.getGuild();
            }
        }, 0);

        EventValues.registerEventValue(TrackEvent.class, AudioTrack.class, new Getter<AudioTrack, TrackEvent>() {
            @Override
            public AudioTrack get(TrackEvent event) {
                return event.getTrack();
            }
        }, 0);

    }

    private Expression<String> bot;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        bot = (Expression<String>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        return ((TrackEvent) e).getState() == TrackEvent.TrackState.START;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "on track start" + (bot == null ? "" : " seen by" + bot.toString(e, debug));
    }
}

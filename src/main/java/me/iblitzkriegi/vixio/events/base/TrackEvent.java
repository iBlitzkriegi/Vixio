package me.iblitzkriegi.vixio.events.base;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class TrackEvent extends SimpleBukkitEvent{
    static {
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

    private TrackState state;
    private Bot bot;
    private Guild guild;
    private AudioTrack track;

    public TrackEvent() {

    }

    public TrackEvent(TrackState state, Bot bot, Guild guild, AudioTrack track) {
        this.state = state;
        this.bot = bot;
        this.guild = guild;
        this.track = track;
    }

    public TrackState getState() {
        return state;
    }

    public Bot getBot() {
        return bot;
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioTrack getTrack() {
        return track;
    }

    public enum TrackState {

        PAUSE, PLAY, START, END, SEEK

    }

}

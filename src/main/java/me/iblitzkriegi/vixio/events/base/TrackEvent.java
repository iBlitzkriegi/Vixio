package me.iblitzkriegi.vixio.events.base;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;

public class TrackEvent extends SimpleBukkitEvent{
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

package me.iblitzkriegi.vixio.util.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.base.TrackEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public void nextTrack() {
        player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
        MusicStorage musicStorage = Vixio.getInstance().musicStorage.get(player);
        if (musicStorage != null) {
            Bukkit.getPluginManager().callEvent(new TrackEvent(TrackEvent.TrackState.END, musicStorage.getBot(), musicStorage.getGuild(), track));
        }

    }


    public AudioPlayer getPlayer() {
        return player;
    }

    public void resetPlayer() {
        queue.clear();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        MusicStorage musicStorage = Vixio.getInstance().musicStorage.get(player);
        if (musicStorage != null) {
            Bukkit.getPluginManager().callEvent(new TrackEvent(TrackEvent.TrackState.START, musicStorage.getBot(), musicStorage.getGuild(), track));
        }
    }

    public ArrayList getQueue() {
        return new ArrayList<>(queue);
    }

    public void shuffleQueue() {
        ArrayList items = new ArrayList<>(queue);
        queue.clear();
        Collections.shuffle(items);
        for (Object track : items) {
            queue.offer((AudioTrack) track);
        }

    }
}

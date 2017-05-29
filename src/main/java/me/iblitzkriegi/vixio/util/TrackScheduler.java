package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackEnd;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackStart;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Blitz on 12/17/2016.
 */public class TrackScheduler extends AudioEventAdapter {
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
        EvntAudioPlayerTrackEnd efc = new EvntAudioPlayerTrackEnd(player, track, endReason, Vixio.reverseGuilds.get(player), EffLogin.audioPlayers.get(player));
        Bukkit.getServer().getPluginManager().callEvent(efc);
        if (endReason.mayStartNext) {
            nextTrack();
        }

    }


    public AudioPlayer getPlayer() {
        return player;
    }
    public void resetPlayer(){
        queue.clear();
    }
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        EvntAudioPlayerTrackStart efc = new EvntAudioPlayerTrackStart(player, track, Vixio.reverseGuilds.get(player), EffLogin.audioPlayers.get(player));
        Bukkit.getServer().getPluginManager().callEvent(efc);
    }
    public ArrayList getQueue() {
        return new ArrayList<>(queue);
    }
}


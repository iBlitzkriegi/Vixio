package me.iblitzkriegi.vixio.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(
        name = "PlayerTrackEnd",
        title = "Player Track End", desc = "Fired when the track ends",
        type = MultiBotGuildCompare.class,
        syntax = "[discord] track (end|stop) by player %string%",
        example = "on track end by player \\\"Rawr\\\"\\n\\tbroadcast \\\"Event seen by %event-player%\\\"")
public class EvntAudioPlayerTrackEnd extends Event {
    private static final HandlerList hls = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return hls;
    }
    public static HandlerList getHandlerList() {
        return hls;
    }
    private AudioPlayer vPlayer;
    private AudioTrack vTrack;
    private AudioTrackEndReason vEndR;
    public EvntAudioPlayerTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason){
        vPlayer = player;
        vTrack = track;
        vEndR = endReason;
    }
    public AudioPlayer getPlayer(){
        return vPlayer;
    }
    public AudioTrack getTrack(){
        return vTrack;
    }
    public String getEndReason(){
        return vEndR.name();
    }

}

package me.iblitzkriegi.vixio.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(name = "PlayerTrackStart", type = MultiBotGuildCompare.class, syntax = "[discord] track (begin|start) by player %string%")
public class EvntAudioPlayerTrackStart extends Event {
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
    public EvntAudioPlayerTrackStart(AudioPlayer player, AudioTrack track){
        vPlayer = player;
        vTrack = track;
    }
    public AudioPlayer getPlayer(){
        return vPlayer;
    }
    public AudioTrack getTrack(){
        return vTrack;
    }

}

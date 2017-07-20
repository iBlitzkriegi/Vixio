package me.iblitzkriegi.vixio.events.audio;

import ch.njol.skript.lang.util.SimpleEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.iblitzkriegi.vixio.registration.annotation.EvntAnnotation;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(
        name = "PlayerTrackEnd",
        title = "Player Track End",
        desc = "Fired when the track ends",
        type = SimpleEvent.class,
        syntax = "[discord] track (end|stop)",
        example = "SUBMIT EXAMPLES TO BLITZ#3273")
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
    private Guild vGuild;
    private User vBot;
    public EvntAudioPlayerTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason, Guild g, User bot){
        vPlayer = player;
        vTrack = track;
        vEndR = endReason;
        vGuild = g;
        vBot = bot;
    }
    public Guild getGuild(){return vGuild;}
    public User getBot(){return vBot;}
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

package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.registration.EvntAnnotation;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blitz on 11/4/2016.
 */
@EvntAnnotation.Event(
        name = "PlayerTrackStart",
        title = "Player Track Start",
        desc = "Fired when the Player starts playing",
        type = SimpleEvent.class,
        syntax = "[discord] track (begin|start)",
        example = "on track end by player \\\"Rawr\\\"\\n\\tbroadcast \\\"Played started by player %event-player%\\\"")
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
    private Guild vGuild;
    private User vBot;
    public EvntAudioPlayerTrackStart(AudioPlayer player, AudioTrack track, Guild g, User bot){
        vPlayer = player;
        vTrack = track;
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

}

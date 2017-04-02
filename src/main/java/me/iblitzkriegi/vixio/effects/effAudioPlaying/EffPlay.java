package me.iblitzkriegi.vixio.effects.effAudioPlaying;

import ch.njol.skript.Skript;
import ch.njol.skript.effects.EffLog;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.audioManagers;
import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "PlayAudio",
        title = "Play Audio",
        desc = "Play audio through your bot! First %string% can be any audio link that when clicked automatically plays audio without having to be logged in! Like Youtube/Soundcloud/etc...",
        syntax = "[discord] play audio %string% with [audio] player [named] %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$play\\\":\\n" +
                "\\t\\tif {_args::2} is set:\\n" +
                "\\t\\t\\tset {_rawr} to event-string\\n" +
                "\\t\\t\\tset {playing} to {_args::2}\\n" +
                "\\t\\t\\treplace all \\\"$play \\\" with \\\"\\\" in {_rawr}\\n" +
                "\\t\\t\\tplay audio {_rawr} with player \\\"Rawr\\\"\\n" +
                "\\t\\t\\treply with \\\"Successfully attempted to play that track for you.\\\"")
public class EffPlay extends Effect {
    Expression<String> vBot;
    Expression<String> vTrackUrl;
    @Override
    protected void execute(Event e) {
        if(EffLogin.trackSchedulers.get(vBot.getSingle(e))!=null||audioManagers.get(vBot.getSingle(e))!=null) {
            TrackScheduler trackScheduler = EffLogin.trackSchedulers.get(vBot.getSingle(e));
            AudioPlayerManager playerManager = audioManagers.get(vBot.getSingle(e));
            playerManager.loadItem(vTrackUrl.getSingle(e), new AudioLoadResultHandler() {

                @Override
                public void trackLoaded(AudioTrack track) {
                    trackScheduler.queue(track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    for (AudioTrack track : playlist.getTracks()) {
                        trackScheduler.queue(track);
                    }
                }

                @Override
                public void noMatches() {
                    Skript.warning("Corrupt audio Url. I can't play anything from this URL!?");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    Skript.warning("Something's gone...So wrong...Oh so very wrong. I messed up loading apparently...?");
                }
            });
        }else{
            Skript.warning("No player exists by the name \"" + vBot.getSingle(e)+"\n");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vTrackUrl = (Expression<String>) expr[0];
        vBot = (Expression<String>) expr[1];
        return true;
    }
}

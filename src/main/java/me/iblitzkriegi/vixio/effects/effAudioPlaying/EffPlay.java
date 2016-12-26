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
@EffectAnnotation.Effect(syntax = "[discord] play audio %string% with [audio] player [named] %string%")
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

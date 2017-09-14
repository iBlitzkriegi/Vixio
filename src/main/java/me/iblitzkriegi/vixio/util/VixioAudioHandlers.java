package me.iblitzkriegi.vixio.util;

import ch.njol.skript.Skript;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.EffLogin;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import static me.iblitzkriegi.vixio.Vixio.musicManagers;
import static me.iblitzkriegi.vixio.Vixio.playerManager;
import static me.iblitzkriegi.vixio.Vixio.reverseGuilds;

/**
 * Created by Blitz on 12/17/2016. >Ready for comments about static abuse
 */
public class VixioAudioHandlers {
    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        String guildId = guild.getId();
        GuildMusicManager musicManager = musicManagers.get(guildId);
        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);

        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public static void loadAndPlay(final Guild guild, final String trackUrl, User bot) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        if(Vixio.reverseGuilds.get(musicManager.player)==null){
            reverseGuilds.put(musicManager.player, guild);
        }
        if(EffLogin.audioPlayers.get(musicManager.player)==null){
            EffLogin.audioPlayers.put(musicManager.player, bot);

        }
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    musicManager.scheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                Skript.warning("Noting found by that Audio link.");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                Skript.warning("Could not load Audio from the provided link.");
            }

        });
    }

    public static void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);

    }

    public static void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();
    }
}

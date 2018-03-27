package me.iblitzkriegi.vixio.util.audio;

import ch.njol.skript.Skript;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;

public class AudioHandlers {
    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild, Bot bot) {
        if (Vixio.getInstance().multiMap.get(guild) != null) {
            ArrayList<GuildMusicManager> guildMusicManagers = Vixio.getInstance().multiMap.get(guild);
            for (GuildMusicManager guildMusicManager : guildMusicManagers) {
                if (guildMusicManager.bot.getJDA() == bot.getJDA()) {
                    return guildMusicManager;
                }
            }

            GuildMusicManager musicManager = new GuildMusicManager(Vixio.getInstance().playerManager, guild, bot);
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            ArrayList<GuildMusicManager> managerArrayList = new ArrayList<>();
            managerArrayList.add(musicManager);
            Vixio.getInstance().multiMap.put(guild, managerArrayList);
            return musicManager;
        }

        GuildMusicManager musicManager = new GuildMusicManager(Vixio.getInstance().playerManager, guild, bot);
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        ArrayList<GuildMusicManager> managerArrayList = new ArrayList<>();
        managerArrayList.add(musicManager);
        Vixio.getInstance().multiMap.put(guild, managerArrayList);
        return musicManager;

    }

    public static void loadAndPlay(final Guild guild, final String trackUrl, Bot bot) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild, bot);
        Vixio.getInstance().playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
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

    public static void skipTrack(Guild guild, Bot bot) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild, bot);
        musicManager.scheduler.nextTrack();
    }
}

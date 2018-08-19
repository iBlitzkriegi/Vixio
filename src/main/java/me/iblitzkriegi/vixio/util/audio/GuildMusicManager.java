package me.iblitzkriegi.vixio.util.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;

public class GuildMusicManager {

    private AudioPlayer player;
    private TrackScheduler scheduler;
    private Guild guild;
    private Bot bot;

    public GuildMusicManager(Guild guild, Bot bot) {
        player = Vixio.getInstance().playerManager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        this.guild = guild;
        this.bot = bot;
        MusicStorage musicStorage = new MusicStorage(bot, guild);
        Vixio.getInstance().musicStorage.put(player, musicStorage);
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public Guild getGuild() {
        return guild;
    }

    public Bot getBot() {
        return bot;
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }

}
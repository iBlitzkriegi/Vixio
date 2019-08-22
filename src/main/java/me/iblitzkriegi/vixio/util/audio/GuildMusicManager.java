package me.iblitzkriegi.vixio.util.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {
    public final AudioPlayer player;
    public final TrackScheduler scheduler;
    public final Guild guild;
    public final Bot bot;

    public GuildMusicManager(Guild guild, Bot bot) {
        player = Vixio.getInstance().playerManager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        this.guild = guild;
        this.bot = bot;
        MusicStorage musicStorage = new MusicStorage(bot, guild);
        Vixio.getInstance().musicStorage.put(player, musicStorage);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }

}
package me.iblitzkriegi.vixio.util.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;

public class GuildMusicManager {
    public final AudioPlayer player;
    public final TrackScheduler scheduler;
    public final Guild guild;
    public final Bot bot;
    public GuildMusicManager(AudioPlayerManager manager, Guild guild, Bot bot) {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        this.guild = guild;
        this.bot = bot;

    }
    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }
}
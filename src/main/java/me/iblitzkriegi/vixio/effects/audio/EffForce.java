package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;

public class EffForce extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffForce.class,
                "force [%bot/string% to] play %string/track% [in %guild%]")
                .setName("Force Play")
                .setDesc("This will force a bot to play a track and set the current playing track to be next in the queue. If the input isn't a single track or not able to be found it will not affect the bot.")
                .setExample("force play \"some youtube url im too lazy to go find one\" in event-guild");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;
    private Expression<Object> audio;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (guild == null || bot == null) {
            return;
        }

        GuildMusicManager musicManager = bot.getAudioManager(guild);
        Object track = this.audio.getSingle(e);
        if (track instanceof AudioTrack) {
            AudioTrack audioTrack = (AudioTrack) track;
            musicManager.scheduler.queue(audioTrack);
            EffPlay.lastLoaded.put(musicManager, audioTrack);
        } else if (track instanceof String) {
            if (!(StringUtils.startsWithIgnoreCase((String) track, "ytsearch:") || StringUtils.startsWithIgnoreCase((String) track, "scsearch:"))) {
                Vixio.getInstance().playerManager.loadItemOrdered(musicManager, (String) track, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        musicManager.scheduler.forceTrackToPlay(track);
                        EffPlay.lastLoaded.put(musicManager, track);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {

                    }

                    @Override
                    public void noMatches() {
                        Vixio.getErrorHandler().warn("Vixio attempted to load " + track + " but was unable to find anything.");
                    }

                    @Override
                    public void loadFailed(FriendlyException exception) {
                        Vixio.getErrorHandler().warn("Vixio attempted to load " + track + " but was unable to.");
                    }
                });
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "force " + bot.toString(e, debug) + " to play " + audio.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        audio = (Expression<Object>) exprs[1];
        guild = (Expression<Guild>) exprs[2];
        return true;
    }
}

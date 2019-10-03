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

import java.util.HashMap;

public class EffPlay extends Effect {

    public static HashMap<GuildMusicManager, AudioTrack> lastLoaded = new HashMap<>();

    static {
        Vixio.getInstance().registerEffect(EffPlay.class, "play %strings/tracks% [in %guild%] [with %bot/string%]")
                .setName("Play audio")
                .setDesc("Play a specific audio track or attempt to load something from a URL")
                .setExample("play \"https://www.youtube.com/watch?v=elwTgpHlty0\" in guild with id \"2199673352656165156\" with \"Jewel\"");
    }

    private Expression<Guild> guild;
    private Expression<Object> audio;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Guild guild = this.guild.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null || guild == null) {
            return;
        }

        GuildMusicManager musicManager = bot.getAudioManager(guild);
        for (Object track : this.audio.getAll(e)) {
            if (track instanceof AudioTrack) {
                AudioTrack audioTrack = (AudioTrack) track;
                musicManager.scheduler.queue(audioTrack);
                lastLoaded.put(musicManager, audioTrack);
            } else if (track instanceof String) {
                if (!(StringUtils.startsWithIgnoreCase((String) track, "ytsearch:") || StringUtils.startsWithIgnoreCase((String) track, "scsearch:"))) {
                    Vixio.getInstance().playerManager.loadItemOrdered(musicManager, (String) track, new AudioLoadResultHandler() {
                        @Override
                        public void trackLoaded(AudioTrack track) {
                            musicManager.scheduler.queue(track);
                            lastLoaded.put(musicManager, track);

                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist playlist) {
                            for (AudioTrack track : playlist.getTracks()) {
                                musicManager.scheduler.queue(track);
                            }
                            lastLoaded.put(musicManager, playlist.getTracks().get(playlist.getTracks().size() - 1));
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

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "play " + audio.toString(e, debug) + " in " + guild.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        audio = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }

}

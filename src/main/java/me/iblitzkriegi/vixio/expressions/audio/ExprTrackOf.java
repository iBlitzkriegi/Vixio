package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class ExprTrackOf extends SimpleExpression<AudioTrack> {
    static {
        Vixio.getInstance().registerExpression(ExprTrackOf.class, AudioTrack.class, ExpressionType.SIMPLE,
                "[the] track[s] %bot/string% is playing [in %guild%]")
                .setName("Track Bot Is Playing")
                .setDesc("Get the tracks a bot is playing in a specific guild.")
                .setExample(
                        "discord command $info:",
                        "\ttrigger:",
                        "\t\tset {_track} to track event-bot is playing",
                        "\t\tif {_track} is not set:",
                        "\t\t\treply with \"I am not currently playing anything!\"",
                        "\t\t\tstop",
                        "\t\treply with \"I am currently playing %name of {_track}% by %author of {_track}%\""
                );
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected AudioTrack[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (guild == null || bot == null) {
            return null;
        }

        GuildMusicManager musicManager = bot.getAudioManager(guild);
        return new AudioTrack[]{musicManager.player.getPlayingTrack()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends AudioTrack> getReturnType() {
        return AudioTrack.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "track " + bot.toString(e, debug) + " is playing in " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

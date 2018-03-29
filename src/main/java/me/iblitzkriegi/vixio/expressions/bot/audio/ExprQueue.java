package me.iblitzkriegi.vixio.expressions.bot.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.audio.TrackScheduler;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.List;

public class ExprQueue extends SimpleExpression<AudioTrack> {
    static {
        Vixio.getInstance().registerExpression(ExprQueue.class, AudioTrack.class, ExpressionType.SIMPLE,
                "queue of %bot/string% [in %guild%]")
                .setName("Queue of bot")
                .setDesc("Get all the tracks a bot currently has queued up for a guild.")
                .setExample("set {var::*} to queue of event-bot in event-guild");
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
        TrackScheduler scheduler = musicManager.scheduler;
        if (scheduler.getQueue().isEmpty()) {
            return null;
        }

        List<AudioTrack> tracks = scheduler.getQueue();
        return tracks.toArray(new AudioTrack[tracks.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends AudioTrack> getReturnType() {
        return AudioTrack.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "queue of " +  bot.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

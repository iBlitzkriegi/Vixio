package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.audio.EffPlay;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class ExprLastLoadedTrack extends SimpleExpression<AudioTrack> {
    static {
        Vixio.getInstance().registerExpression(ExprLastLoadedTrack.class, AudioTrack.class, ExpressionType.SIMPLE,
                "[the] last loaded [audio] track [of %bot/string%] [in %guild%]")
                .setName("Last Loaded Audio Track")
                .setDesc("Get the last track a bot has loaded. This does not mean the track the bot is playing.")
                .setExample("reply with \"%the last loaded track%\"");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected AudioTrack[] get(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (bot == null || guild == null) {
            return null;
        }
        GuildMusicManager musicManager = bot.getAudioManager(guild);
        AudioTrack track = EffPlay.lastLoaded.get(musicManager);
        return new AudioTrack[]{track};

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
        return "last loaded track of " + bot.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

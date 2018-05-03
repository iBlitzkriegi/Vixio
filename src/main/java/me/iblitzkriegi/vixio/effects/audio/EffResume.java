package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class EffResume extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffResume.class,
                "resume [the] (track|song|music) %bot/string% (is playing|has paused) [in %guild%]")
                .setName("Resume track")
                .setDesc("Resume a track a bot is playing in a guild, if the bot is not paused then nothing happens")
                .setExample("resume the track event-bot is playing");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (guild == null || bot == null) {
            return;
        }

        GuildMusicManager musicManager = bot.getAudioManager(guild);
        if (!musicManager.player.isPaused()) {
            return;
        }

        musicManager.player.setPaused(false);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "resume " + bot.toString(e, debug) + "'s track in " + guild.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

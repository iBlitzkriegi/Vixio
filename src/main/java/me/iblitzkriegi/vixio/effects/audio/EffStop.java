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

public class EffStop extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffStop.class,
                "stop [the] (track|song) %bot/string% is playing [in %guild%]")
                .setName("Stop track")
                .setDesc("Stop a track a bot is playing in a guild")
                .setExample("stop the track event-bot is playing");
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
        if (musicManager.player.getPlayingTrack() != null) {
            musicManager.player.stopTrack();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "stop " + bot.toString(e, debug) + "'s track in " + guild.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return false;
    }
}

package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.AudioHandlers;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class EffSkip extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffSkip.class, "skip [the] (track|song)[s] %bot/string% is playing [in %guilds%]")
                .setName("Skip track")
                .setDesc("Skip the current track a bot is playing in a guild")
                .setExample("skip track event-bot is playing in event-guild");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (bot == null || guild == null) {
            return;
        }

        AudioHandlers.skipTrack(guild, bot);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "skip track " + bot.toString(e, debug) + " is playing in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

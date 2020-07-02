package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class EffShuffle extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffShuffle.class,
                "shuffle [the] queue of %bot/string% [in %guild%]")
                .setName("Shuffle Queue")
                .setDesc("This will randomize a bots queue")
                .setExample("shuffle the queue of event-bot in event-guild");
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
        musicManager.scheduler.shuffleQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "shuffle the queue of " + bot.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

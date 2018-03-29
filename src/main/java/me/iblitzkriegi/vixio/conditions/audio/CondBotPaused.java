package me.iblitzkriegi.vixio.conditions.audio;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class CondBotPaused extends Condition {
    static {
        Vixio.getInstance().registerCondition(CondBotPaused.class,
                "%bot/string% is paused [in %guild%]", "%bot/string% (is|are)(n't| not) paused [in %guild%]")
                .setName("Bot paused")
                .setDesc("Tell if a bot is paused or not.")
                .setExample("if event-bot isn't paused:");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;
    private boolean not;

    @Override
    public boolean check(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (bot == null || guild == null) {
            return false;
        }
        return not == bot.getAudioManager(guild).player.isPaused();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return bot.toString(e, debug) + (not ? " is " : " is not ") + " paused in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        not = matchedPattern == 0;
        return true;
    }
}

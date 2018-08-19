package me.iblitzkriegi.vixio.conditions.audio;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class CondBotPaused extends Condition {
    static {
        Vixio.getInstance().registerCondition(CondBotPaused.class,
                "%bots/strings% (is|are) paused [in %guild%]",
		"%bots/strings% (is|are)(n't| not) paused [in %guild%]")
                .setName("Bot Is Paused")
                .setDesc("Tell if a bot is paused or not.")
                .setExample("if event-bot isn't paused:");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    public boolean check(Event e) {
        Guild guild = this.guild.getSingle(e);

        if (guild == null)
            return false;

        return EasyMultiple.check(Util.botFrom(bot.getArray(e)),
                bot -> bot.getAudioManager(guild).getPlayer().isPaused(), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return bot.toString(e, debug) + (isNegated() ? " is " : " is not ") + " paused in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }
}

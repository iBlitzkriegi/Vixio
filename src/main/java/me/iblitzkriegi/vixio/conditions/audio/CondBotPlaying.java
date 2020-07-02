package me.iblitzkriegi.vixio.conditions.audio;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class CondBotPlaying extends Condition {
    static {
        Vixio.getInstance().registerCondition(CondBotPlaying.class,
                "%bot/string% is playing [in %guild%]", "%bot/string% (is|are)(n't| not) playing [in %guild%]")
                .setName("Bot is playing")
                .setDesc("Check if a bot is playing something in a guild")
                .setExample("if event-bot is playing");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;
    private boolean not;

    @Override
    public boolean check(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (guild == null || bot == null) {
            return false;
        }

        return not == (bot.getAudioManager(guild).player.getPlayingTrack() != null);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return bot.toString(e, debug) + (not ? " is " : " is not ") + "playing in " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        not = matchedPattern == 0;
        return true;
    }
}

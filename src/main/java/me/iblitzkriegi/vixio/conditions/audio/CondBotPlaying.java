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

public class CondBotPlaying extends Condition {
    static {
        Vixio.getInstance().registerCondition(CondBotPlaying.class,
                "%bots/strings% (is|are) playing [in %guild%]",
                "%bots/strings% (is|are)(n't| not) playing [in %guild%]")
                .setName("Bot Is Playing")
                .setDesc("Check if a bot is playing something in a guild")
                .setExample("if event-bot is playing");
    }

    private Expression<Object> bots;
    private Expression<Guild> guild;

    @Override
    public boolean check(Event e) {
        Guild guild = this.guild.getSingle(e);

        if (guild == null) {
            return false;
        }

        return EasyMultiple.check(Util.botFrom(bots.getArray(e)),
                bot -> bot.getAudioManager(guild).getPlayer().getPlayingTrack() != null, isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return bots.toString(e, debug) + (isNegated() ? " is " : " is not ") + "playing in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bots = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }

}

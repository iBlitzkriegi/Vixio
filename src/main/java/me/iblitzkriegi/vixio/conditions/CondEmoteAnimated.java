package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import org.bukkit.event.Event;

public class CondEmoteAnimated extends Condition implements EasyMultiple<Emote, Void> {
    static {
        Vixio.getInstance().registerCondition(CondEmoteAnimated.class,
                "%emotes% (is|are) animated", "%emotes% (is|are)(n't| not) animated")
                .setName("Emote is Animated")
                .setDesc("Check if a emote is animated.")
                .setExample("if event-emote is animated:");
    }

    private Expression<Emote> emotes;

    @Override
    public boolean check(Event e) {
        return check(emotes.getAll(e), emote -> emote.isAnimated(), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return emotes.toString(e, debug) + " is " + (isNegated() ? " not " : "") + "animated";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        emotes = (Expression<Emote>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}

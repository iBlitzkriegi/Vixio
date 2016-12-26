package me.iblitzkriegi.vixio.effects.effembeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "(clear|delete) embed %string%")
public class EffDeleteEmbed extends Effect{
    Expression<String> vEmbed;
    @Override
    protected void execute(Event e) {
        EffCreateEmbed.embedBuilders.put(vEmbed.getSingle(e), null);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vEmbed = (Expression<String>) expr[0];
        return true;
    }
}

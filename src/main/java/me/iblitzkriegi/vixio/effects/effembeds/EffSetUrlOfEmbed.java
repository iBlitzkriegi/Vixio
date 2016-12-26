package me.iblitzkriegi.vixio.effects.effembeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "set url of embed %string% to %string%")
public class EffSetUrlOfEmbed extends Effect{
    Expression<String> vName;
    Expression<String> vTitle;
    @Override
    protected void execute(Event e) {
        EmbedBuilder builder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        builder.setUrl(vTitle.getSingle(e));
        EffCreateEmbed.embedBuilders.put(vName.getSingle(e), builder);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vTitle = (Expression<String>) expr[1];
        return true;
    }
}

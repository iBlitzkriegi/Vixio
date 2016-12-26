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
@EffectAnnotation.Effect(syntax = "add field %string%, with value %string%, split %boolean% to embed %string%")
public class EffAddFieldToEmbed extends Effect{
    Expression<String> vField;
    Expression<String> vValue;
    Expression<Boolean> vInline;
    Expression<String> vEmbed;
    @Override
    protected void execute(Event e) {
        EmbedBuilder builder = EffCreateEmbed.embedBuilders.get(vEmbed.getSingle(e));
        builder.addField(vField.getSingle(e).replaceAll("%nl%", "\n"), vValue.getSingle(e).replaceAll("%nl%", "\n"), vInline.getSingle(e));
        EffCreateEmbed.embedBuilders.put(vEmbed.getSingle(e), builder);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vField = (Expression<String>) expr[0];
        vValue = (Expression<String>) expr[1];
        vInline = (Expression<Boolean>) expr[2];
        vEmbed = (Expression<String>) expr[3];
        return true;
    }
}

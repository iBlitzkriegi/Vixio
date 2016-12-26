package me.iblitzkriegi.vixio.effects.effembeds;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import javafx.scene.paint.Color;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.util.HashMap;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "set author of embed %string% to embed titled %string%, with hyperlink %string%, iconurl %string%")
public class EffSetAuthorOfEmbed extends Effect{
    Expression<String> vTitle;
    Expression<String> vName;
    Expression<String> vHyper;
    Expression<String> vIcon;
    public static HashMap<String, EmbedBuilder> embedBuilders = new HashMap<>();
    @Override
    protected void execute(Event e) {
        try {
            EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
            if (embedBuilder != null) {
                embedBuilder.setAuthor(vTitle.getSingle(e), vHyper.getSingle(e), vIcon.getSingle(e));
                embedBuilders.put(vName.getSingle(e), embedBuilder);
            } else {
                Skript.warning("No EmbedBuilder exists by that name.");
            }
        }catch (NullPointerException x){
            x.printStackTrace();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vTitle = (Expression<String>) expr[1];
        vHyper = (Expression<String>) expr[2];
        vIcon = (Expression<String>) expr[3];
        return true;
    }
}

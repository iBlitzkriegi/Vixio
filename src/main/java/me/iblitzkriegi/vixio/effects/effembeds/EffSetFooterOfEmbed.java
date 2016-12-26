package me.iblitzkriegi.vixio.effects.effembeds;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "set footer of embed %string% to %string% [with icon %-string%]")
public class EffSetFooterOfEmbed extends Effect{
    Expression<String> vName;
    Expression<String> vText;
    Expression<String> vIconUrl;
    public static HashMap<String, EmbedBuilder> embedBuilders = new HashMap<>();
    @Override
    protected void execute(Event e) {
        EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        if(vIconUrl!=null) {
            embedBuilder.setFooter(vText.getSingle(e), vIconUrl.getSingle(e));
        }else{
            embedBuilder.setFooter(vText.getSingle(e), null);
        }
        embedBuilders.put(vName.getSingle(e), embedBuilder);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vText = (Expression<String>) expr[1];
        vIconUrl = (Expression<String>) expr[2];
        return true;
    }
}

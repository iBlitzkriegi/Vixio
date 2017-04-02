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
@EffectAnnotation.Effect(
        name = "SetColorOfEmbed",
        title = "Set Color Of Embed",
        desc = "Set the color of a Embed",
        syntax = "set color of embed %string% to %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$update\\\":\\n" +
                "\\t\\tmake embed \\\"Vixio110\\\"\\n" +
                "\\t\\tset color of embed \\\"Vixio110\\\" to \\\"BLUE\\\"\\n" +
                "\\t\\tsend embed \\\"Vixio110\\\" to channel event-channel with \\\"Rawr\\\"\\n" +
                "\\t\\tclear embed \\\"Vixio110\\\"")
public class EffSetColorOfEmbed extends Effect{
    Expression<String> vName;
    Expression<String> vColor;
    public static HashMap<String, EmbedBuilder> embedBuilders = new HashMap<>();
    @Override
    protected void execute(Event e) {
        EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
            Color color;
            try {
                Field field = Class.forName("java.awt.Color").getField(vColor.getSingle(e));
                color = (Color)field.get(null);
                embedBuilder.setColor(color);
            } catch (Exception x) {
                Skript.warning("Color " + vColor.getSingle(e) + " could not be found");
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
        vColor = (Expression<String>) expr[1];
        return true;
    }
}

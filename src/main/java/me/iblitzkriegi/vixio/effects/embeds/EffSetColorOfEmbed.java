package me.iblitzkriegi.vixio.effects.embeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
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
            try {
                embedBuilder.setColor(Color.decode(vColor.getSingle(e)));
            } catch (NumberFormatException x) {
               try{
                   final Field f = Color.class.getField(vColor.getSingle(e));
                   embedBuilder.setColor((Color)f.get(null));
               } catch (IllegalAccessException e1) {
                   e1.printStackTrace();
               } catch (NoSuchFieldException e1) {
                   e1.printStackTrace();
               }
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

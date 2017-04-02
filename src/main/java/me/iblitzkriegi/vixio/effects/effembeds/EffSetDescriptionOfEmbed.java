package me.iblitzkriegi.vixio.effects.effembeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.util.HashMap;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "SetDescOfEmbed",
        title = "Set Description Of Embed",
        desc = "Set the description of one of your Embeds",
        syntax = "set desc[ription] of embed %string% to %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$update\\\":\\n" +
                "\\t\\tmake embed \\\"Vixio110\\\"\\n" +
                "\\t\\tset desc of embed \\\"Vixio110\\\" to \\\"New Events and made all the syntaxes more new Skript user friendly!\\\""+
                "\\t\\tsend embed \\\"Vixio110\\\" to channel event-channel with \\\"Rawr\\\"")
public class EffSetDescriptionOfEmbed extends Effect{
    Expression<String> vName;
    Expression<String> vDesc;
    public static HashMap<String, EmbedBuilder> embedBuilders = new HashMap<>();
    @Override
    protected void execute(Event e) {
        EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        embedBuilder.setDescription(vDesc.getSingle(e));
        embedBuilders.put(vName.getSingle(e), embedBuilder);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vDesc = (Expression<String>) expr[1];
        return true;
    }
}

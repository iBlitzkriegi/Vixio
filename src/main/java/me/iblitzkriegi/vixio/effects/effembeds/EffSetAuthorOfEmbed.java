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
@EffectAnnotation.Effect(
        name = "SetAuthorOfEmbed",
        title = "Set Author Of Embed",
        desc = "Set the author of a embed",
        syntax = "set author of embed %string% to embed titled %string%, with hyperlink %string%, iconurl %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$update\\\":\\n" +
                "\\t\\tmake embed \\\"Vixio110\\\"\\n" +
                "\\t\\tset author of embed \\\"Vixio110\\\" to embed titled \\\"Vixio 1.1.0 has been released!\\\", with hyperlink \\\"https://github.com/iBlitzkriegi/Vixio/releases/tag/v1.1.0\\\", iconurl \\\"http://i.imgur.com/lI71VQR.jpg\\\"\\n" +
                "\\t\\tsend embed \\\"Vixio110\\\" to channel event-channel with \\\"Rawr\\\"\\n" +
                "\\t\\tclear embed \\\"Vixio110\\\"")
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

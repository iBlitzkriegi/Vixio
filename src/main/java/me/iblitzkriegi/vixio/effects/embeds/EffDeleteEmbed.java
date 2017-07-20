package me.iblitzkriegi.vixio.effects.embeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "DeleteEmbed",
        title = "Delete Embed",
        desc = "Delete a existing embed builder you've created",
        syntax = "(clear|delete) embed %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$nowplaying\\\":\\n" +
                "\\t\\tset {ptsd} to track player {name} is playing\\n" +
                "\\t\\tmake embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tset title of embed \\\"Nowplaying\\\" to \\\"Displaying information for the track that is currently playing\\\"\\n" +
                "\\t\\tset color of embed \\\"Nowplaying\\\" to \\\"PINK\\\"\\n" +
                "\\t\\tadd field \\\"**Title**\\\", with value \\\"%title of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tsend embed \\\"Nowplaying\\\" to channel event-channel with \\\"Rawr\\\"\\n"+
                "\\t\\tclear embed \\\"Nowplaying\\\"")
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

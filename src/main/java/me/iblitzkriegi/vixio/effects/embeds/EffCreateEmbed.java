package me.iblitzkriegi.vixio.effects.embeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.util.HashMap;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "CreateEmbed",
        title = "Create Embed",
        desc = "Create a embed",
        syntax = "(make|create) embed %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$nowplaying\\\":\\n" +
                "\\t\\tset {ptsd} to track player {name} is playing\\n" +
                "\\t\\tclear embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tmake embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tset title of embed \\\"Nowplaying\\\" to \\\"Displaying information for the track that is currently playing\\\"\\n" +
                "\\t\\tset color of embed \\\"Nowplaying\\\" to \\\"PINK\\\"\\n" +
                "\\t\\tadd field \\\"**Title**\\\", with value \\\"%title of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tadd field \\\"**Author**\\\", with value \\\"%author of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tadd field \\\"**Duration**\\\", with value \\\"%duration of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tadd field \\\"**Identifier**\\\", with value \\\"%identifier of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tadd field \\\"**Position**\\\", with value \\\"%position of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tsend embed \\\"Nowplaying\\\" to channel event-channel with \\\"Rawr\\\"")
public class EffCreateEmbed extends Effect{
    Expression<String> vName;
    public static HashMap<String, EmbedBuilder> embedBuilders = new HashMap<>();
    @Override
    protected void execute(Event e) {
        EmbedBuilder builder = new EmbedBuilder();
        embedBuilders.put(vName.getSingle(e), builder);

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.embeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "AddFieldToEmbed",
        title = "Add Field To Embed",
        desc = "Add a field to a embed, must create one first!",
        syntax = "add field %string%, with value %string%, split %boolean% to embed %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$nowplaying\\\":\\n" +
                "\\t\\tset {ptsd} to track player {name} is playing\\n" +
                "\\t\\tmake embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tadd field \\\"**Title**\\\", with value \\\"%title of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tsend embed \\\"Nowplaying\\\" to channel event-channel with \\\"Rawr\\\"\\n"+
                "\\t\\tclear embed \\\"Nowplaying\\\"")
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

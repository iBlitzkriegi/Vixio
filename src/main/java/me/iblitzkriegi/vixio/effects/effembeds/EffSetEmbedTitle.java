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
@EffectAnnotation.Effect(
        name = "TitleOfEmbed",
        title = "Set Title Of Embed",
        desc = "Set the Title of one of your Embeds",
        syntax = "set title of embed %string% to %string%[ with url %-string%]",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\t if {_command} starts with \\\"$guild\\\":\\n" +
                "\\t\\tmake embed \\\"Guild\\\"\\n" +
                "\\t\\tset title of embed \\\"Guild\\\" to \\\"What is %name of event-guild%\\\"\\n" +
                "\\t\\tset thumbnail of embed \\\"Guild\\\" to \\\"%avatar url of event-guild%\\\"\\n" +
                "\\t\\tset desc of embed \\\"Guild\\\" to \\\"Name: **[%name of event-guild%](.)**%nl%ID: **[%id of event-guild%](.)**\\\"\\n" +
                "\\t\\tsend embed \\\"Guild\\\" to channel event-channel with \\\"Rawr\\\"")
public class EffSetEmbedTitle extends Effect{
    Expression<String> vName;
    Expression<String> vTitle;
    Expression<String> vUrl;
    @Override
    protected void execute(Event e) {
        EmbedBuilder builder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        if(vUrl!=null) {
            builder.setTitle(vTitle.getSingle(e), vUrl.getSingle(e));
        }else{
            builder.setTitle(vTitle.getSingle(e), null);
        }
        EffCreateEmbed.embedBuilders.put(vName.getSingle(e), builder);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vTitle = (Expression<String>) expr[1];
        vUrl = (Expression<String>) expr[2];
        return true;
    }
}

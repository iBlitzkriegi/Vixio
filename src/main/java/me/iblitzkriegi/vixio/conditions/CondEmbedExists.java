package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.embeds.EffCreateEmbed;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/18/2016.
 */
@CondAnnotation.Condition(
        name = "EmbedExists",
        title = "Embed Exists",
        desc = "Check if a Embed builder exists",
        syntax = "embed [named] %string% exists",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$exist\\\":\\n" +
                "\\t\\tif embed \\\"Vixio110\\\" exists:\\n" +
                "\\t\\t\\tclear embed \\\"Vixio110\\\"\\n" +
                "\\t\\telse:\\n" +
                "\\t\\t\\tmake embed \\\"Vixio110\\\"")
public class CondEmbedExists extends Condition {
    Expression<String> vName;
    @Override
    public boolean check(Event e) {
        if(EffCreateEmbed.embedBuilders.get(vName.getSingle(e))!=null){
            return true;
        }
        return false;
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

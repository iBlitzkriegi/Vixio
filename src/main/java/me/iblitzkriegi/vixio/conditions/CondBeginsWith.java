package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */
@CondAnnotation.Condition(
        name = "BeginsWith",
        title = "Begins With",
        desc = "Check if a string begins/starts with another string",
        syntax = "%string% (begins|starts) with %string%",
        example = "on guild message received seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$ping\\\":\\n" +
                "\\t\\treply with \\\"pong\\\"")
public class CondBeginsWith extends Condition {
    private Expression<String> string1;
    private Expression<String> string2;
    @Override
    public boolean check(Event e) {
        if(string1.getSingle(e).startsWith(string2.getSingle(e))) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        string1 = (Expression<String>) expr[0];
        string2 = (Expression<String>) expr[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.effembeds.EffCreateEmbed;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/18/2016.
 */
@CondAnnotation.Condition(syntax = "embed [named] %string% exists")
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

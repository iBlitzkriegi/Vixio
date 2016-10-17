package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/14/2016.
 */
public class CondLoggedIn extends Condition {
    @Override
    public boolean check(Event e) {
        boolean isloggedin;
        if(getAPI().getJDA()!=null){
            isloggedin = true;
        }else{
            isloggedin = false;
        }
        return isloggedin;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

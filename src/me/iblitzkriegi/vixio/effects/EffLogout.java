package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/14/2016.
 */
public class EffLogout extends Effect {
    private Expression<String> botname;
    @Override
    protected void execute(Event e) {
        if(getAPI().getJDA()!=null){
            getAPI().getJDA().shutdown();
            EffLogin.s = null;
        }else{
            Skript.warning("There was no bot logged in, therefore none could be logged out.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

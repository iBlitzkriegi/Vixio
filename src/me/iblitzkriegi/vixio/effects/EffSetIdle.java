package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/16/2016.
 */
public class EffSetIdle extends Effect {
    private Expression<Boolean> isidle;
    @Override
    protected void execute(Event e) {
        getAPI().getJDA().getAccountManager().setIdle(isidle.getSingle(e));
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        isidle = (Expression<Boolean>) expr[0];
        return true;
    }
}

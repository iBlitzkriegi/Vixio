package me.iblitzkriegi.vixio.effects.effGenerals;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/15/2016.
 */
public class EffSetGame extends Effect {
    private Expression<String> game;
    @Override
    protected void execute(Event e) {
        getAPI().getJDA().getAccountManager().setGame(game.getSingle(e));
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        game = (Expression<String>) expressions[0];
        return true;
    }
}

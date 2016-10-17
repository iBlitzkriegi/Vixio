package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/15/2016.
 */
public class EffSendTyping extends Effect {
    private Expression<String> id;
    @Override
    protected void execute(Event e) {
        if(getAPI().getJDA().getTextChannelById(id.getSingle(e))!=null) {
            getAPI().getJDA().getTextChannelById(id.getSingle(e)).sendTyping();
        }else{
            Skript.warning("Could not find TextChannel by that id.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expr[0];
        return true;
    }
}

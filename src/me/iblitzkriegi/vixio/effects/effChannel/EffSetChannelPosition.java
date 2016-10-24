package me.iblitzkriegi.vixio.effects.effChannel;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.api.API;
import net.dv8tion.jda.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/23/2016.
 */
public class EffSetChannelPosition extends Effect {
    private Expression<String> id;
    private Expression<Integer> position;
    @Override
    protected void execute(Event e) {
        try {
            API.getAPI().getJDA().getTextChannelById(id.getSingle(e)).getManager().setPosition(position.getSingle(e)).update();
        }catch (PermissionException x){
            Skript.warning(x.getLocalizedMessage());
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expr[0];
        position = (Expression<Integer>) expr[1];
        return true;
    }
}

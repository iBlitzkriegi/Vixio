package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.api.API;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/15/2016.
 */
public class EffReply extends Effect{
    private Expression<String> message;
    private Expression<String> channel;
    @Override
    protected void execute(Event e) {
        API.getAPI().getJDA().getTextChannelById(channel.getSingle(e)).sendMessage(message.getSingle(e));

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<String>) expressions[0];
        channel = (Expression<String>) expressions[1];
        return true;
    }
}

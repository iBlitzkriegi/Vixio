package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.MessageBuilder;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/16/2016.
 */
public class EffMakeBuild extends Effect {
    private Expression<String> message2build;
    private Expression<String> id;
    @Override
    protected void execute(Event e) {
        MessageBuilder builder = new MessageBuilder();
        String rawr2b = message2build.getSingle(e).replaceAll(":::", "\n");
        builder.appendString("```").appendString("\n");
        builder.appendString(rawr2b).appendString("\n");
        builder.appendString("```").appendString("\n");
        getAPI().getJDA().getTextChannelById(id.getSingle(e)).sendMessage(builder.build());
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message2build = (Expression<String>) expr[0];
        id = (Expression<String>) expr[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.expressions.exprChannel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.entities.Message;
import org.bukkit.event.Event;

import java.util.List;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/20/2016.
 */
public class ExprPinnedMessages extends SimpleExpression<List> {
    Expression<String> id;

    @Override
    protected List[] get(Event e) {
        List<Message> s = getAPI().getJDA().getTextChannelById(id.getSingle(e)).getPinnedMessages();
        if (s != null) {
            return new List[]{s};
        } else {
            return null;
        }
    }
    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends List> getReturnType() {
        return List.class;
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

package me.iblitzkriegi.vixio.expressions.exprGuild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.api.API;
import net.dv8tion.jda.entities.Guild;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/22/2016.
 */
public class ExprSizeOfGuild extends SimpleExpression<String> {
    private Expression<String> size;
    @Override
    protected String[] get(Event e) {
        return new String[]{getGuildSize(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        size = (Expression<String>) expr[0];
        return true;
    }
    public String getGuildSize(Event e){
        Guild s = API.getAPI().getJDA().getGuildById(size.getSingle(e));
        return String.valueOf(s.getUsers().size());
    }
}

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
public class ExprOwnerOfGuild extends SimpleExpression<String> {
    private Expression<String> guild;
    @Override
    protected String[] get(Event e) {
        return new String[]{getGuildName(e)};
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
        guild = (Expression<String>) expr[0];
        return true;
    }
    public String getGuildName(Event e){
        Guild s = API.getAPI().getJDA().getGuildById(guild.getSingle(e));
        return s.getOwner().getId();
    }
}

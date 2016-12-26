package me.iblitzkriegi.vixio.expressions.botInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import java.util.List;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(returntype = List.class, type = ExpressionType.SIMPLE, syntax = "[discord] %string%('s|s) guilds")
public class ExprGuilds extends SimpleExpression<List> {
    Expression<String> vBot;
    @Override
    protected List[] get(Event e) {
        return new List[]{getGuilds(e)};
    }

    @Override
    public boolean isSingle() {
        return false;
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
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) e[0];
        return true;
    }
    private List getGuilds(Event e){
        JDA jda = bots.get(vBot.getSingle(e));
        return jda.getGuilds();
    }
}

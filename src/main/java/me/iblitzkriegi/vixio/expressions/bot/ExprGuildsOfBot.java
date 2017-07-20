package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 12/26/2016.
 */
@ExprAnnotation.Expression(
        name = "GuildsOfBot",
        title = "Guilds of Bot",
        desc = "Get the Guilds of a Bot, loopable",
        syntax = "guilds of bot %string%",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprGuildsOfBot extends SimpleExpression<Guild> {
    Expression<String> vBot;
    @Override
    protected Guild[] get(Event e) {
        return getGuilds(e).toArray(new Guild[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expressions[0];
        return true;
    }
    private List<Guild> getGuilds(Event e){
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        if(jda!=null){
            return jda.getGuilds();
        }
        Skript.warning("Bot not found by that name.");
        return null;
    }
}

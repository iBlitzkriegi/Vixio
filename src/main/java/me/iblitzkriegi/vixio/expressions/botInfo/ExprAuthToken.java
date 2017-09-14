package me.iblitzkriegi.vixio.expressions.botInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(
        name = "AuthTokenOfBot",
        title = "Auth Token of Bot",
        desc = "Get the Auth Token of your bot (DO NOT SHOW THIS TO ANYONE)",
        syntax = "auth token of bot %string%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprAuthToken extends SimpleExpression<String> {
    Expression<String> vBot;
    @Override
    protected String[] get(Event e) {
        return new String[]{getAuth(e)};
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
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) e[0];
        return true;
    }
    private String getAuth(Event e){
        JDA jda = bots.get(vBot.getSingle(e));
        return jda.getToken();
    }
}

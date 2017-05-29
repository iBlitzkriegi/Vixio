package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(
        name = "GameOUsercr",
        title = "Current game of User",
        desc = "Get the Game of a User in a Guild",
        syntax = "current game of user [with id] %string% in guild [with id] %string%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprGameOf extends SimpleExpression<String> {
    private Expression<String> vID;
    private Expression<String> vGuild;
    @Override
    protected String[] get(Event e) {
        return new String[]{getGame(e)};
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
        vID = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        return true;
    }
    private String getGame(Event e) {
        try {
            for (Map.Entry<String, JDA> u : bots.entrySet()) {
                Guild vG = u.getValue().getGuildById(vGuild.getSingle(e));
                Member member = vG.getMember(u.getValue().getUserById(vID.getSingle(e)));
                if (vG != null || member != null) {
                    return member.getGame().getName();
                } else {
                    Skript.warning("null");
                    return "<none>";
                }
            }

            return "<none>";
        }catch (NullPointerException x){
            return "<none>";
        }
    }

}

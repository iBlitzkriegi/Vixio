package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@ExprAnnotation.Expression(
        name = "OwnerOf",
        title = "Owner Of",
        desc = "Get the Owner of a Guild via it's ID",
        syntax = "owner of guild [with id] %string%",
        returntype = User.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprOwnerOf  extends SimpleExpression<User>{
    private Expression<String> vID;
    @Override
    protected User[] get(Event e) {
        return new User[]{getOwnerOf(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vID = (Expression<String>) expr[0];
        return true;
    }
    private User getOwnerOf(Event e) {
        try {
            for (Map.Entry<String, JDA> u : bots.entrySet()) {
                if (u.getValue().getGuildById(vID.getSingle(e)) != null) {
                    return u.getValue().getGuildById(vID.getSingle(e)).getOwner().getUser();
                }
            }

        }catch (NullPointerException x){
            Skript.warning("Could not find a Guild with that ID.");
        }
        return null;
    }
}

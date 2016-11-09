package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@ExprAnnotation.Expression(returntype = User.class, type = ExpressionType.SIMPLE, syntax = "owner of %string%")
public class ExprOwnerOf  extends SimpleExpression<User>{
    private Expression<User> vID;
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
    private User getOwnerOf(Event e) {
        for (Map.Entry<String, JDA> u : bots.entrySet()) {
            for (Guild vG : u.getValue().getGuilds()) {
                return vG.getOwner();
            }
        }
        return null;
    }
}

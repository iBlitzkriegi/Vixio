package me.iblitzkriegi.vixio.expressions.loopables;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 12/26/2016.
 */
@ExprAnnotation.Expression(returntype = List.class, type = ExpressionType.SIMPLE, syntax = "text[-]channels of bot %string%")
public class ExprUsersOfBot extends SimpleExpression<User> {
    Expression<String> vBot;
    @Override
    protected User[] get(Event e) {
        return getUsers(e).toArray(new User[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
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
        vBot = (Expression<String>) expressions[0];
        return true;
    }
    private List<User> getUsers(Event e){
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        if(jda!=null){
            return jda.getUsers();
        }
        Skript.warning("Bot not found by that name.");
        return null;
    }
}

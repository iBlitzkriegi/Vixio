package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 2/11/2017.
 */
@ExprAnnotation.Expression(
        name = "eventmentioned",
        title = "event-mentioned",
        desc = "Get the mentioned User out of the GuildMessageReceive event",
        syntax = "[event-]mentioned",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprMentioned extends ch.njol.skript.lang.util.SimpleExpression<User> {
    @Override
    protected User[] get(Event e) {
        return getMentioned(e).toArray(new User[0]);
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
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)){
            return true;
        }
        Skript.warning("You may not use `event-mentioned` outside of Discord events!");
        return false;
    }
    private List<User> getMentioned(Event e) {
        if (e instanceof EvntGuildMessageReceive) {
            try {
                List list = ((EvntGuildMessageReceive) e).getMention();
                return list;
            } catch (NullPointerException x) {
                System.out.println("There was no mentioned users!");
            }

        }
        return null;
    }
}

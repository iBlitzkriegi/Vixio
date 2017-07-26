package me.iblitzkriegi.vixio.expressions.eventvalues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.EvntGuildMessageReceived;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprBot extends SimpleExpression<User>{
    static {
        Vixio.registerExpression(ExprBot.class, User.class, ExpressionType.SIMPLE, "event-bot")
                .setName("Event value event-bot")
                .setDesc("Event value that returns the User object of the bot.")
                .setExample("on guild message rec: broadcast \"%event-bot%\"");
    }

    @Override
    protected User[] get(Event event) {
        return new User[]{(getBot(event))};
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
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceived.class)){
            return true;
        }
        Skript.error("There is no event-bot outside of Vixio events!");
        return false;
    }
    private static User getBot(Event e){
        if(e == null){
            return null;
        }
        if(e instanceof EvntGuildMessageReceived){
            return ((EvntGuildMessageReceived) e).getBot();
        }
        return null;
    }
}

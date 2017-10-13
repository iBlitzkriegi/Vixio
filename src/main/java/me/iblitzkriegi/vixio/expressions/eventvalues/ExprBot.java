package me.iblitzkriegi.vixio.expressions.eventValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.EventGuildMessageReceived;
import me.iblitzkriegi.vixio.events.EventJDAEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.SelfUser;
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
    protected SelfUser[] get(Event event) {
        return new SelfUser[]{(getBot(event))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends SelfUser> getReturnType() {
        return SelfUser.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "event-bot in event " + event.getEventName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EventGuildMessageReceived.class)){
            return true;
        }
        Skript.error("There is no event-bot outside of Vixio events!");
        return false;
    }
    private static SelfUser getBot(Event e){
        if(e == null){
            return null;
        }
        if(e instanceof EventGuildMessageReceived){
            return ((EventGuildMessageReceived) e).getJDA().getSelfUser();
        }else if(e instanceof EventJDAEvent){
            if(((EventJDAEvent) e).getObject("JDA") != null){
                if(((EventJDAEvent) e).getObject("JDA") instanceof JDA){
                    return ((JDA) ((EventJDAEvent) e).getObject("JDA")).getSelfUser();
                }
            }
        }
        return null;
    }
}

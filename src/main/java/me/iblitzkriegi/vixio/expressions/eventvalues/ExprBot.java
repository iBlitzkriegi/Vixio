package me.iblitzkriegi.vixio.expressions.eventValues;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import net.dv8tion.jda.core.entities.SelfUser;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/9/2017.
 */
public class ExprBot extends SimpleExpression<SelfUser>{
    static {
        Vixio.registerExpression(ExprBot.class, SelfUser.class, ExpressionType.SIMPLE, "event-bot").setName("event-bot").setDesc("Returns a SelfUser").setExample("event-bot");
    }
    @Override
    protected SelfUser[] get(Event event) {
        return new SelfUser[]{getBot(event)};
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
        return true;
    }
    private static SelfUser getBot(Event e) {
        if (e == null) {
            Skript.error("Event value not present in requested event.");
            return null;
        } else {
            if (e instanceof DiscordEventHandler) {
                if (((DiscordEventHandler) e).getObject("SelfUser") != null) {
                    SelfUser user = (SelfUser) ((DiscordEventHandler) e).getObject("SelfUser");
                    return user;
                }
            }
        }
        Skript.error("Event value not present in requested event.");
        return null;
    }
}

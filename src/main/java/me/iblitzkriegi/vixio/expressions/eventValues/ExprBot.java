package me.iblitzkriegi.vixio.expressions.eventValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.EvntMessageReceived;
import net.dv8tion.jda.core.entities.SelfUser;
import org.bukkit.event.Event;

public class ExprBot extends SimpleExpression<SelfUser> {
    static {
        Vixio.getInstance().registerExpression(ExprBot.class, SelfUser.class, ExpressionType.SIMPLE, "event-bot")
                .setName("event bot")
                .setDesc("The bot that sees a event occur.")
                .setExample("set {var} to event-bot");
    }
    @Override
    protected SelfUser[] get(Event e) {
        return new SelfUser[]{getBot(e)};
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
    public String toString(Event e, boolean debug) {
        return "event-bot";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntMessageReceived.class)){
            return true;
        }
        Skript.error("This may not be used in events that do not have a bot.");
        return false;
    }
    public SelfUser getBot(Event e){
        if(e == null){
            return null;
        }
        if(e instanceof EvntMessageReceived){
            return ((EvntMessageReceived) e).getJDA().getSelfUser();
        }
        return null;
    }
}

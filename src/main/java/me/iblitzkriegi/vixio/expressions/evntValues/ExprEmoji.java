package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntMessageAddReaction;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 2/26/2017.
 */
@ExprAnnotation.Expression(
        name = "eventemoji",
        title = "event-emoji",
        desc = "Get the Emoji out of MessageAddReaction Event",
        syntax = "[event-]emoji",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprEmoji extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getEmoji(e)};
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntMessageAddReaction.class)){
            return true;
        }
        Skript.warning("You may not use \"event-emoji\" outside of the MessageAddReaction event");
        return false;
    }
    private String getEmoji(Event e){
        if(e==null){
            return null;
        }else if(e instanceof EvntMessageAddReaction){
            return ((EvntMessageAddReaction) e).getEvntEmoji();
        }
        return "null";
    }
}

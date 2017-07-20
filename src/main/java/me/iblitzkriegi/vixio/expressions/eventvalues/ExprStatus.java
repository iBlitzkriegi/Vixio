package me.iblitzkriegi.vixio.expressions.eventvalues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.member.EvntUserStartStreaming;
import me.iblitzkriegi.vixio.events.member.EvntUserStatusChange;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/7/2016.
 */
@ExprAnnotation.Expression(
        name = "eventstatus",
        title = "event-status",
        desc = "Get the new status out of the Presence changing events",
        syntax = "[event-]status",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprStatus extends SimpleExpression<String>{
    @Override
    protected String[] get(Event e) {
        return new String[]{getOnlineStatus(e)};
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
        if(ScriptLoader.isCurrentEvent(EvntUserStatusChange.class)
                | ScriptLoader.isCurrentEvent(EvntUserStartStreaming.class)
                ){
            return true;
        }
        Skript.warning("You may not use event-status outside of Discord events.");
        return false;
    }
    private static String getOnlineStatus(Event e){
        if(e == null){
            return null;
        }if (e instanceof EvntUserStartStreaming) {
            return ((EvntUserStartStreaming) e).getEvntCur();
        }
        return "idfk how you managed to get this value but...Uh..Hi?";
    }
}
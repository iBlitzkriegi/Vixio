package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/18/2016.
 */
@ExprAnnotation.Expression(
        name = "IdentifierOfTrack",
        title = "Identifier Of Track",
        desc = "Get the Identifier of a track",
        syntax = "identifier of track %object%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprIdentifierOfTrack extends SimpleExpression<String> {
    Expression<Object> vTrack;
    @Override
    protected String[] get(Event e) {
        return new String[]{getInfo(e)};
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
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vTrack = (Expression<Object>) expr[0];
        return true;
    }
    private String getInfo(Event e){
        if(vTrack.getSingle(e) instanceof AudioTrack){
            return ((AudioTrack) vTrack.getSingle(e)).getInfo().identifier;
        }else{
            System.out.println(vTrack.getSingle(e) + " is the object idiot. Not a AudioTrack.");
            return "Something dun fuk'd'd'd'd'";
        }
    }
}

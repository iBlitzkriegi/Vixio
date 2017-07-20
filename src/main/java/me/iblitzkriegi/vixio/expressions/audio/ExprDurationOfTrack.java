package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/18/2016.
 */
@ExprAnnotation.Expression(
        name = "DurationOfTrack",
        title = "Duration Of Track",
        desc = "Get the total Duration of a track",
        syntax = "duration of track %object%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprDurationOfTrack extends SimpleExpression<String> {
    Expression<Object> vTrack;
    @Override
    protected String[] get(Event e) {
        return new String[]{getDuration(e)};
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
    private String getDuration(Event e){
        if(vTrack.getSingle(e) instanceof AudioTrack){
            if(((AudioTrack) vTrack.getSingle(e)).getDuration() / 1000 % 60 < 10){
                return String.valueOf(((AudioTrack) vTrack.getSingle(e)).getDuration() / 1000 / 60 + ":0" + ((AudioTrack) vTrack.getSingle(e)).getDuration() / 1000 % 60);
            }else{
                return String.valueOf(((AudioTrack) vTrack.getSingle(e)).getDuration()/1000/60 + ":" + ((AudioTrack) vTrack.getSingle(e)).getDuration() / 1000 % 60);
            }
        }else{
            return "Something dun fuk'd'd'd'd'";
        }
    }
}

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
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "identifier of track %track%")
public class ExprIdentifierOfTrack extends SimpleExpression<String> {
    private static Expression<AudioTrack> vTrack;
    @Override
    protected String[] get(Event e) {
        return new String[]{getIdentifier(e)};
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
        vTrack = (Expression<AudioTrack>) expr[0];
        return true;
    }
    private static String getIdentifier(Event e){
        AudioTrack track = vTrack.getSingle(e);
        return track.getInfo().identifier;
    }
}

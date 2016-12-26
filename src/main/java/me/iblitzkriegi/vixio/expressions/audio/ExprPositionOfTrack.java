package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/18/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "position of track %track%")
public class ExprPositionOfTrack extends SimpleExpression<String> {
    private static Expression<com.sedmelluq.discord.lavaplayer.track.AudioTrack> vTrack;
    @Override
    protected String[] get(Event e) {
        return new String[]{getPosition(e)};
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
        vTrack = (Expression<com.sedmelluq.discord.lavaplayer.track.AudioTrack>) expr[0];
        return true;
    }
    private static String getPosition(Event e){
        com.sedmelluq.discord.lavaplayer.track.AudioTrack track = vTrack.getSingle(e);
        if(track.getDuration()/1000%60 < 10) {
            return String.valueOf(track.getPosition() / 1000 / 60 + ":0" + track.getPosition() / 1000 % 60).toString();
        }
        return String.valueOf(track.getPosition() / 1000 / 60 + ":" + track.getPosition() / 1000 % 60).toString();
    }
}

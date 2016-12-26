package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blitz on 12/19/2016.
 */
@ExprAnnotation.Expression(returntype = List.class, type = ExpressionType.SIMPLE, syntax = "queue of [audio] player %string%")
public class ExprPlayerQueueOf extends SimpleExpression<AudioTrack> {
    private static Expression<String> vBot;
    @Override
    protected AudioTrack[] get(Event e) {
        return getQueue(e).toArray(new AudioTrack[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends AudioTrack> getReturnType() {
        return AudioTrack.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        return true;
    }
    private static List<AudioTrack> getQueue(Event e) {
        TrackScheduler scheduler = EffLogin.trackSchedulers.get(vBot.getSingle(e));
        if (scheduler == null) {
            throw new IllegalStateException("OH NO! SCHEDULER IS NULL!");
        }

        List<AudioTrack> tracks = scheduler.getQueue();
        if (tracks == null) {
            throw new IllegalStateException("OH NO! TRACKS LIST IS NULL!");
        }
        for (AudioTrack track : tracks) {
            if (track == null) {
                throw new IllegalStateException("OH NO! THERE IS A NULL TRACK IN THE QUEUE");
            } else if (track.getInfo() == null) {
                throw new IllegalStateException("OH NO! THE TRACK INFO IS NULL");
            } else if (track.getInfo().title == null) {
                throw new IllegalStateException("OH NO! THE TITLE IS NULL");
            } else if (track.getInfo().author == null) {
                throw new IllegalStateException("OH NO! THE AUTHOR IS NULL");
            }
        }
        System.out.println("All is BUENO! The issue is elsewhere.");
        return tracks;
    }
}

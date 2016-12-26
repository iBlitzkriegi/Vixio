package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackEnd;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackStart;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/18/2016.
 */
@ExprAnnotation.Expression(returntype = AudioTrack.class, type = ExpressionType.SIMPLE, syntax = "[event-]track")
public class ExprTrack extends SimpleExpression<AudioTrack> {
    @Override
    protected AudioTrack[] get(Event e) {
        return new AudioTrack[]{getPlayer(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntAudioPlayerTrackStart.class)) {
            return true;
        }else{
            Skript.warning("You may not use event-track outside of Audio events!");
            return false;
        }
    }
    private static AudioTrack getPlayer(Event e){
        if(e instanceof EvntAudioPlayerTrackStart){
            return ((EvntAudioPlayerTrackStart) e).getTrack();
        }else if(e instanceof EvntAudioPlayerTrackEnd){
            return ((EvntAudioPlayerTrackEnd) e).getTrack();
        }
        return null;
    }
}

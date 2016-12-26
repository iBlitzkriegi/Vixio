package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackEnd;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackStart;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

import java.util.Map;

/**
 * Created by Blitz on 12/18/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[event-]reason")
public class ExprEndReason extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getPlayer(e)};
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
        if(ScriptLoader.isCurrentEvent(EvntAudioPlayerTrackEnd.class)) {
            return true;
        }
        Skript.warning("You may not use event-reason outside of the \"[discord] track (end|stop) by player %string%\" event!");
        return false;
    }
    private static String getPlayer(Event e){
        if(e instanceof EvntAudioPlayerTrackEnd){
            return ((EvntAudioPlayerTrackEnd) e).getEndReason();
        }
        return null;
    }
}

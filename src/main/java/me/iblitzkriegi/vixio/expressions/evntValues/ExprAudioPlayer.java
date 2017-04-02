package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackEnd;
import me.iblitzkriegi.vixio.events.EvntAudioPlayerTrackStart;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

import java.util.Map;

/**
 * Created by Blitz on 12/18/2016.
 */
@ExprAnnotation.Expression(
        name = "eventaudioplayer",
        title = "event-audioplayer",
        desc = "Returns the Player from the TrackEnd and TrackStart events",
        syntax = "[event-]audioplayer",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprAudioPlayer extends SimpleExpression<String> {
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
        return true;
    }
    private static String getPlayer(Event e){
        if(e instanceof EvntAudioPlayerTrackStart){
            for(Map.Entry<String, AudioPlayer> player : EffLogin.audioPlayers.entrySet()){
                if(((EvntAudioPlayerTrackStart) e).getPlayer().equals(player)){
                    return player.getKey();
                }
            }
        }else if(e instanceof EvntAudioPlayerTrackEnd){
            for(Map.Entry<String, AudioPlayer> player : EffLogin.audioPlayers.entrySet()){
                if(((EvntAudioPlayerTrackEnd) e).getPlayer().equals(player)){
                    return player.getKey();
                }
            }
        }
        return null;
    }
}

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
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[event-]audioplayer")
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
            System.out.println("Is Start Event");
            for(Map.Entry<String, AudioPlayer> player : EffLogin.audioPlayers.entrySet()){
                System.out.println("Is Looping in start");
                if(((EvntAudioPlayerTrackStart) e).getPlayer().equals(player)){
                    System.out.println("Found player in start");
                    return player.getKey();
                }
            }
        }else if(e instanceof EvntAudioPlayerTrackEnd){
            System.out.println("Is End Event");
            for(Map.Entry<String, AudioPlayer> player : EffLogin.audioPlayers.entrySet()){
                System.out.println("Is looping in end");
                if(((EvntAudioPlayerTrackEnd) e).getPlayer().equals(player)){
                    System.out.println("found play in end");
                    return player.getKey();
                }
            }
        }
        return null;
    }
}

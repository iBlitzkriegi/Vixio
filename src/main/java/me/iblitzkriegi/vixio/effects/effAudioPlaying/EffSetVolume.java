package me.iblitzkriegi.vixio.effects.effAudioPlaying;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.audioPlayers;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "set [audio] player %string% volume to %integer%")
public class EffSetVolume extends Effect {
    Expression<String> vBot;
    Expression<Integer> vVolume;
    @Override
    protected void execute(Event e) {
        if(audioPlayers.get(vBot.getSingle(e))!=null) {
            AudioPlayer player = audioPlayers.get(vBot.getSingle(e));
            player.setVolume(vVolume.getSingle(e));
        }else{
            Skript.warning("No player exists by the name \"" + vBot.getSingle(e)+"\n");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vVolume = (Expression<Integer>) expr[1];
        return true;
    }
}

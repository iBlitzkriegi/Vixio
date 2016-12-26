package me.iblitzkriegi.vixio.effects.effAudioPlaying;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.audioPlayers;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "stop playing track with %string%")
public class EffStopTrack extends Effect {
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        if(audioPlayers.get(vBot.getSingle(e))!=null) {
            AudioPlayer player = audioPlayers.get(vBot.getSingle(e));
            player.stopTrack();

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
        return true;
    }
}

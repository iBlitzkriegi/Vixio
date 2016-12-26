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

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "skip current track player %string% is playing")
public class EffSkipTrack extends Effect {
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        if(EffLogin.trackSchedulers.get(vBot.getSingle(e))!=null) {
            TrackScheduler trackScheduler = EffLogin.trackSchedulers.get(vBot.getSingle(e));
            trackScheduler.nextTrack();
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

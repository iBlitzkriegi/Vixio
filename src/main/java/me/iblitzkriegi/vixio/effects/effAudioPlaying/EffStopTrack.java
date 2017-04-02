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
@EffectAnnotation.Effect(
        name = "StopPlayerTrack",
        title = "Stop Player Track",
        desc = "Stop the current track of one of your players",
        syntax = "stop playing track player [named] %string% is playing",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$stop\\\":\\n" +
                "\\t\\tif player \\\"Rawr\\\" is playing audio:\\n" +
                "\\t\\t\\tstop playing track player \\\"Rawr\\\" is playing\\n" +
                "\\t\\t\\treply with \\\"Stopped the track\\\"\\n" +
                "\\t\\telse:\\n" +
                "\\t\\t\\treply with \\\"Wasnt playing anything anyways\\\""
)
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

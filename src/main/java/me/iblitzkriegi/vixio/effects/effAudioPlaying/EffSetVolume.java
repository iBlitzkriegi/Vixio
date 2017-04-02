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
@EffectAnnotation.Effect(
        name = "SetVolumeOfPlayer",
        title = "Set Volume Of Player",
        desc = "Set the volume of the player to a number",
        syntax = "set [audio] player %string% volume to %number%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$volume\\\":\\n" +
                "\\t\\tif {_args::2} is set:\\n" +
                "\\t\\t\\tset player \\\"Rawr\\\" volume to {_args::2}\\n" +
                "\\t\\t\\treply with \\\"Set the volume to %{_args::2}%\\\"")
public class EffSetVolume extends Effect {
    Expression<String> vBot;
    Expression<Number> vVolume;
    @Override
    protected void execute(Event e) {
        if(audioPlayers.get(vBot.getSingle(e))!=null) {
            AudioPlayer player = audioPlayers.get(vBot.getSingle(e));
            player.setVolume(Integer.valueOf(String.valueOf(vVolume.getSingle(e))));
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
        vVolume = (Expression<Number>) expr[1];
        return true;
    }
}
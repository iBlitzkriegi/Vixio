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
        name = "SetPlayerPaused",
        title = "Set Player Paused",
        desc = "Set the paused state of a player",
        syntax = "set [audio] player %string% [audio] paused state to %boolean%",
        example = "on guild message received seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$pause\\\":\\n" +
                "\\t\\tif bot \\\"Rawr\\\" is playing audio:\\n" +
                "\\t\\t\\tset player \\\"Rawr\\\" paused state to true\\n" +
                "\\t\\t\\treply with \\\"You have successfully reset the player.\\\"\\n" +
                "\\t\\telse:\\n" +
                "\\t\\t\\treply with \\\"Player Paused\\\""
)
public class EffSetPaused extends Effect {
    Expression<String> vBot;
    Expression<Boolean> vBoolean;
    @Override
    protected void execute(Event e) {
        if(audioPlayers.get(vBot.getSingle(e))!=null) {
            AudioPlayer player = audioPlayers.get(vBot.getSingle(e));
            player.setPaused(vBoolean.getSingle(e));
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
        vBoolean = (Expression<Boolean>) expr[1];
        return true;
    }
}

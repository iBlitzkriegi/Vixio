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
@EffectAnnotation.Effect(
        name = "ResetPlayer",
        title = "Reset Player",
        desc = "Reset one of your AudioPlayers",
        syntax = "(reset|clear) [audio] player [named] %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$reset\\\":\\n" +
                "\\t\\tif bot \\\"Rawr\\\" is playing audio:\\n" +
                "\\t\\t\\treset player \\\"Rawr\\\"\\n" +
                "\\t\\t\\treply with \\\"You have successfully reset the player.\\\"\\n" +
                "\\t\\telse:\\n" +
                "\\t\\t\\treply with \\\"You weren't playing anything\\\"")
public class EffResetPlayer extends Effect {
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        if(EffLogin.trackSchedulers.get(vBot.getSingle(e))!=null) {
            TrackScheduler trackScheduler = EffLogin.trackSchedulers.get(vBot.getSingle(e));
            trackScheduler.resetPlayer();
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

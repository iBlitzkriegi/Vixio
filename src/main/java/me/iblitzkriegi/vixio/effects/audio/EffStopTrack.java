package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.util.VixioAudioHandlers.getGuildAudioPlayer;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "StopPlayerTrack",
        title = "Stop Player Track",
        desc = "Stop the current track of one of your players",
        syntax = "stop playing track player [named] %string% is playing in guild %string%",
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
    Expression<String> vGuild;
    @Override
    protected void execute(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            musicManager.scheduler.getPlayer().stopTrack();
        }else{
            Skript.warning("Could not find Guild via that ID");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        return true;
    }
}

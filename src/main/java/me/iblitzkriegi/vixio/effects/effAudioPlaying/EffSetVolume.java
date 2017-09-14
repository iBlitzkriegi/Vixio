package me.iblitzkriegi.vixio.effects.effAudioPlaying;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.audioPlayers;
import static me.iblitzkriegi.vixio.util.VixioAudioHandlers.getGuildAudioPlayer;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "SetVolumeOfPlayer",
        title = "Set Volume Of Player",
        desc = "Set the volume of the player to a number",
        syntax = "set [audio] player %string% in guild %string% volume to %number%",
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
    Expression<String> vGuild;
    @Override
    protected void execute(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            musicManager.scheduler.getPlayer().setVolume(Integer.valueOf(String.valueOf(vVolume.getSingle(e))));
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
        vVolume = (Expression<Number>) expr[2];
        return true;
    }
}
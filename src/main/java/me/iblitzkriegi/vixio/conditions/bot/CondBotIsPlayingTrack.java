package me.iblitzkriegi.vixio.conditions.bot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.util.VixioAudioHandlers.getGuildAudioPlayer;

/**
 * Created by Blitz on 12/17/2016.
 */
@CondAnnotation.Condition(
        name = "PlayerIsPlayingTrack",
        title = "Player is playing Track",
        desc = "Check if a player is playing a track",
        syntax = "bot %string% is playing audio in guild %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$reset\\\":\\n" +
                "\\t\\tif bot \\\"Rawr\\\" is playing audio:\\n" +
                "\\t\\t\\treset player \\\"Rawr\\\"\\n" +
                "\\t\\t\\treply with \\\"You have successfully reset the player.\\\"")

public class CondBotIsPlayingTrack extends Condition{
    Expression<String> vBot;
    Expression<String> vGuild;
    @Override
    public boolean check(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            if(musicManager.player.getPlayingTrack()!=null){
                return true;
            }else{
                return false;
            }
        }else{
            Skript.warning("Could not find Guild via that ID");
        }
        return false;
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

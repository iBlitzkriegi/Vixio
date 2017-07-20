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
        name = "SetPlayerPaused",
        title = "Set Player Paused",
        desc = "Set the paused state of a player",
        syntax = "set [audio] player %string% in guild %string% [audio] paused state to %boolean%",
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
    Expression<String> vGuild;
    @Override
    protected void execute(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            musicManager.scheduler.getPlayer().setPaused(vBoolean.getSingle(e));
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
        vBoolean = (Expression<Boolean>) expr[2];
        return true;
    }
}

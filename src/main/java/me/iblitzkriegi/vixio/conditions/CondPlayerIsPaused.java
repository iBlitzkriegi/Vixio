package me.iblitzkriegi.vixio.conditions;

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
 * Created by Blitz on 12/18/2016.
 */
@CondAnnotation.Condition(
        name = "PlayerIsPaused",
        title = "Player is paused",
        desc = "Check if a player is paused",
        syntax = "[audio] player %string% is paused in guild [with id] %string%",
        example = "if player \\\"Rawr\\\" is paused")
public class CondPlayerIsPaused extends Condition {
    Expression<String> vBot;
    Expression<String> vGuild;
    @Override
    public boolean check(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild g;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            g = jda.getGuildById(vGuild.getSingle(e));
            GuildMusicManager musicManager = getGuildAudioPlayer(g);
            return musicManager.player.isPaused();
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

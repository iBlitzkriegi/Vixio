package me.iblitzkriegi.vixio.effects.effBotSetters;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(
        name = "SetGame",
        title = "Set Game",
        desc = "Set the game of one of your bot's!",
        syntax = "[discord] set game of bot %string% to %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$setgame\\\":\\n" +
                "\\t\\tset game of bot \\\"Rawr\\\" to {_args::2}\\n" +
                "\\t\\treply with \\\"Game updated.\\\"")
public class EffSetGame extends Effect{
    Expression<String> vBot;
    Expression<String> vGame;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        jda.getPresence().setGame(Game.of(vGame.getSingle(e)));

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vGame = (Expression<String>) expr[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.bot;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(
        name = "SetStreaming",
        title = "Set Streaming",
        desc = "Set the streaming state of the bot",
        syntax = "[discord] set streaming of bot %string% to stream titled %string% with url %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$setidle\\\":\\n" +
                "\\t\\tif {_args::2} starts with \\\"true\\\":\\n" +
                "\\t\\t\\tset {_rawr} to true\\n" +
                "\\t\\telse if {_args::2} starts with \\\"false\\\":\\n" +
                "\\t\\t\\tset {_rawr} to false\\n" +
                "\\t\\tset streaming of bot \\\"Rawr\\\" stream titled {_args::2} with url {_args::3}\\n" +
                "\\t\\treply with \\\"Updated.\\\"")
public class EffSetStreaming extends Effect{
    Expression<String> vBot;
    Expression<String> vTitle;
    Expression<String> vUrl;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        jda.getPresence().setGame(Game.of(vTitle.getSingle(e), vUrl.getSingle(e)));
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vTitle = (Expression<String>) expr[1];
        vUrl = (Expression<String>) expr[2];
        return true;
    }
}

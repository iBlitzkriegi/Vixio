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
@EffectAnnotation.Effect(syntax = "[discord] set streaming of bot %string% stream titled %string% with url %string%")
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

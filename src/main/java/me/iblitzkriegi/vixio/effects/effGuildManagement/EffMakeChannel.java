package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/1/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] (make|create) [discord] [text-]channel [named] %string% in [guild] %string% as %string%")
public class EffMakeChannel extends Effect {
    private Expression<String> sName;
    private Expression<String> vGuild;
    private Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        createChannel(e);
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sName = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }

    private void createChannel(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        Guild vG = jda.getGuildById(vGuild.getSingle(e));
        vG.getController().createTextChannel(sName.getSingle(e)).queue();
    }

}

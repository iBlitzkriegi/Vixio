package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] set channel %string% name to %string% with %string%")
public class EffSetChannelName extends Effect{
    Expression<String> vChannel;
    Expression<String> vTopic;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        TextChannel vTc = jda.getTextChannelById(vChannel.getSingle(e));
        vTc.getManager().setName(vTopic.getSingle(e)).queue();
    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vChannel = (Expression<String>) expr[0];
        vTopic = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

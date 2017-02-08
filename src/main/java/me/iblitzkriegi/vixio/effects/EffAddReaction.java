package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 2/5/2017.
 */
@EffectAnnotation.Effect(syntax = "vixio add reaction %string% to message %message%")
public class EffAddReaction extends Effect{
    Expression<String> vReaction;
    Expression<Message> vMessage;
    @Override
    protected void execute(Event e) {
        vMessage.getSingle(e).addReaction(":" + vReaction.getSingle(e) + ":").queue();

    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vReaction = (Expression<String>) expr[0];
        vMessage = (Expression<Message>) expr[1];
        return true;
    }
}

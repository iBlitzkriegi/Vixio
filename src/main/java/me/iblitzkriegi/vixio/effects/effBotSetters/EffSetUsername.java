package me.iblitzkriegi.vixio.effects.effBotSetters;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] set username of bot %string% to %string%")
public class EffSetUsername extends Effect {
    Expression<String> vBot;
    Expression<String> vName;
    @Override
    protected void execute(Event e) {
        try {
            JDA jda = bots.get(vBot.getSingle(e));
            jda.getSelfUser().getManager().setName(vName.getSingle(e)).queue();
        }catch (NullPointerException x){
            Skript.warning("Could not find a bot by that name.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vName = (Expression<String>) expr[1];
        return true;
    }
}

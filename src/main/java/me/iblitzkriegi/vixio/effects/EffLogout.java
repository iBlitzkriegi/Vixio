package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.*;

/**
 * Created by Blitz on 11/19/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] logout of bot %string%")
public class EffLogout extends Effect{
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        if(bots.get(vBot.getSingle(e))!=null){
            JDA jda = bots.get(vBot.getSingle(e));
            jda.shutdown();
            bots.put(vBot.getSingle(e), null);
//            musicPlayerHashMap.put(vBot.getSingle(e),null);
            users.put(vBot.getSingle(e), null);
            botruntime.put(vBot.getSingle(e), null);
        }else{
            Skript.warning("No bot with that name is logged in currently.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        return true;
    }
}

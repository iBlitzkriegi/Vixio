package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "send discord message of %string% to %string% with %string%")
public class EffSendUserMessage extends Effect {
    Expression<String> vMessage;
    Expression<String> vUser;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        if(jda.getUserById(vUser.getSingle(e))!=null){
            jda.getUserById(vUser.getSingle(e)).getPrivateChannel().sendMessage(vMessage.getSingle(e)).queue();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<String>) expr[0];
        vUser = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects;

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
 * Created by Blitz on 10/30/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord ]send message %string% to channel %string% as %string%")
public class EffSendMessage extends Effect {
    private Expression<String> sMsg;
    private Expression<String> sChannel;
    private Expression<String> sBot;
    @Override
    protected void execute(Event e) {
        if(bots.get(sBot.getSingle(e))!=null) {
            JDA jda = bots.get(sBot.getSingle(e));
            jda.getTextChannelById(sChannel.getSingle(e)).sendMessage(sMsg.getSingle(e)).queue();
        }else{
            Skript.warning("Could not find bot with the name: " + sBot.getSingle(e));
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sMsg = (Expression<String>) expr[0];
        sChannel = (Expression<String>) expr[1];
        sBot = (Expression<String>) expr[2];
        return true;
    }
}

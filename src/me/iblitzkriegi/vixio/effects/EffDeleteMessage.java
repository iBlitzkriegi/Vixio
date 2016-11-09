package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.TextChannel;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] delete message %string% with %string%")
public class EffDeleteMessage extends Effect {
    Expression<String> vMessage;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        for(TextChannel tc : jda.getTextChannels()){
            if(tc.getMessageById(vMessage.getSingle(e))!=null){
                tc.getMessageById(vMessage.getSingle(e)).deleteMessage();
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<String>) expr[0];
        vBot = (Expression<String>) expr[1];
        return true;
    }
}

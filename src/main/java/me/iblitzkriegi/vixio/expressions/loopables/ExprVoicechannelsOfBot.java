package me.iblitzkriegi.vixio.expressions.loopables;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 12/26/2016.
 */
@ExprAnnotation.Expression(returntype = List.class, type = ExpressionType.SIMPLE, syntax = "voicechannels of bot %string%")
public class ExprVoicechannelsOfBot extends SimpleExpression<VoiceChannel> {
    Expression<String> vBot;
    @Override
    protected VoiceChannel[] get(Event e) {
        return getVoiceChannels(e).toArray(new VoiceChannel[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expressions[0];
        return true;
    }
    private List<VoiceChannel> getVoiceChannels(Event e){
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        if(jda!=null){
            return jda.getVoiceChannels();
        }
        Skript.warning("Bot not found by that name.");
        return null;
    }
}

package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "voice channel of %string%")
public class ExprVoiceChannelOf extends SimpleExpression<String> {
    Expression<String> vID;
    @Override
    protected String[] get(Event e) {
        return new String[]{getVc(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vID = (Expression<String>) expr[0];
        return true;
    }
    private String getVc(Event e){
        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            for(VoiceChannel s : jda.getValue().getVoiceChannels()){
                for(Member r : s.getMembers()){
                    if(r.getUser().getId().equalsIgnoreCase(vID.getSingle(e))){
                        return s.getId();
                    }
                }
            }
        }
        return "null";
    }
}

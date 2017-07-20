package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.member.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(
        name = "IdOf",
        title = "ID of",
        desc = "Get the ID of anything in Discord",
        syntax = "id of %string%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprIdOf extends SimpleExpression<String> {
    private Expression<String> id;

    @Override
    protected String[] get(Event e) {
        return new String[]{getID(e)};
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        return true;
    }

    private String getID(Event e) {
        for(Map.Entry<String, JDA> u : bots.entrySet()){
            if(u.getValue().getUserById(id.getSingle(e))==null){
                if(u.getValue().getGuildById(id.getSingle(e))==null){
                    if(u.getValue().getTextChannelById(id.getSingle(e))==null){
                        if(u.getValue().getVoiceChannelById(id.getSingle(e))==null){
                            if(u.getValue().getRoleById(id.getSingle(e))==null){
                                Skript.warning("Could not find anything with that ID.");
                                return null;
                            }else{
                                return u.getValue().getRoleById(id.getSingle(e)).getId();
                            }
                        }else{
                            return u.getValue().getVoiceChannelById(id.getSingle(e)).getId();
                        }
                    }else{
                        return u.getValue().getTextChannelById(id.getSingle(e)).getId();
                    }
                }else{
                    return u.getValue().getGuildById(id.getSingle(e)).getId();
                }
            }else{
                return u.getValue().getUserById(id.getSingle(e)).getId();
            }
        }
        if(e instanceof EvntGuildMemberLeave){
            return ((EvntGuildMemberLeave) e).getEvntUser().getId();
        }

        return null;
    }

}


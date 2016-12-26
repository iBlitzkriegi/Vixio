package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[discord] name of %string%")
public class ExprNameOf extends SimpleExpression<String> {
    private Expression<String> id;
    @Override
    protected String[] get(Event e) {
        return new String[]{getName(e)};
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
        id = (Expression<String>) expr[0];
        return true;
    }
    @Nullable
    private String getName(Event e) {
        for(Map.Entry<String, JDA> u : bots.entrySet()){
            if(u.getValue().getUserById(id.getSingle(e))==null){
                if(u.getValue().getGuildById(id.getSingle(e))==null){
                    if(u.getValue().getTextChannelById(id.getSingle(e))==null){
                        if(u.getValue().getVoiceChannelById(id.getSingle(e))==null){
                            for(Guild s : u.getValue().getGuilds()){
                                for(Role r : s.getRoles()){
                                    if(r.getId().equalsIgnoreCase(id.getSingle(e))){
                                        return r.getName();
                                    }
                                }
                            }
                        }else{
                            return u.getValue().getVoiceChannelById(id.getSingle(e)).getName();
                        }
                    }else{
                        return u.getValue().getTextChannelById(id.getSingle(e)).getName();
                    }
                }else{
                    return u.getValue().getGuildById(id.getSingle(e)).getName();
                }
            }else{
                return u.getValue().getUserById(id.getSingle(e)).getName();
            }
        }

        return null;
    }
}

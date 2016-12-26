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
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[discord] mention tag of %string%")
public class ExprMentionOf extends SimpleExpression<String> {
    private Expression<String> id;
    @Override
    protected String[] get(Event e) {
        return new String[]{getMention(e)};
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
    private String getMention(Event e) {
        for(Map.Entry<String, JDA> u : bots.entrySet()){
            if(u.getValue().getUserById(id.getSingle(e))!=null){
                return u.getValue().getUserById(id.getSingle(e)).getAsMention();
            }else if (u.getValue().getTextChannelById(id.getSingle(e))!=null){
                return u.getValue().getTextChannelById(id.getSingle(e)).getAsMention();
            }else{
                for(Guild g : u.getValue().getGuilds()){
                    for(Role r : g.getRoles()){
                        if(r.getId().equalsIgnoreCase(id.getSingle(e))){
                            return r.getAsMention();
                        }
                    }

                }
            }
        }
        return null;
    }
}

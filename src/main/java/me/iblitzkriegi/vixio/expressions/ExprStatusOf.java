package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[discord] [online] status of %string% in guild %string%")
public class ExprStatusOf extends SimpleExpression<String> {
    private Expression<String> vID;
    private Expression<String> vGuild;
    @Override
    protected String[] get(Event e) {
        return new String[]{getStatus(e)};
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
    @Nullable
    private String getStatus(Event e) {
        for (Map.Entry<String, JDA> u : bots.entrySet()) {
            Guild vG = u.getValue().getGuildById(vGuild.getSingle(e));
            Member member = u.getValue().getGuildById(vGuild.getSingle(e)).getMemberById(vID.getSingle(e));
            if(member!=null||vG!=null){
                if(!member.getOnlineStatus().name().equalsIgnoreCase("UNKNOWN")){
                    return member.getOnlineStatus().name();
                }else{
                    return "Do Not Disturb";
                }
            }else{
                return "null";
            }

        }
        return "null";
    }

}

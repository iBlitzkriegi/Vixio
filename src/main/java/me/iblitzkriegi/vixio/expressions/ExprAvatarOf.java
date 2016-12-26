package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "(avatar|icon) url of %string%")
public class ExprAvatarOf extends SimpleExpression<String> {
    private Expression<String> vID;
    @Override
    protected String[] get(Event e) {
        return new String[]{getAvatar(e)};
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
    private String getAvatar(Event e) {
        for (Map.Entry<String, JDA> u : bots.entrySet()) {
            if(u.getValue().getUserById(vID.getSingle(e))!=null){
                return u.getValue().getUserById(vID.getSingle(e)).getName();
            }else if(u.getValue().getGuildById(vID.getSingle(e))!=null){
                return u.getValue().getGuildById(vID.getSingle(e)).getIconUrl();
            }
        }
        return null;
    }

}

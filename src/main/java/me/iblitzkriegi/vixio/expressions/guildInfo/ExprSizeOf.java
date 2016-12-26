package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[discord] size of [guild|server] %string%")
public class ExprSizeOf extends SimpleExpression<String> {
    private Expression<String> vID;

    @Override
    protected String[] get(Event e) {
        return new String[]{getSize(e)};
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

    private String getSize(Event e) {
        for (Map.Entry<String, JDA> u : bots.entrySet()) {
            for (Guild vg : u.getValue().getGuilds()) {
                if (vg.getId().equalsIgnoreCase(vID.getSingle(e))) {
                    return String.valueOf(vg.getMembers().size());
                }
            }

        }
        return "Could not find guild by that ID.";
    }
}

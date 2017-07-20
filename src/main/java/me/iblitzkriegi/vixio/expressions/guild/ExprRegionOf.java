package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@ExprAnnotation.Expression(
        name = "RegionOf",
        title = "Region Of Guild",
        desc = "Get the region of a Guild Via its ID",
        syntax = "region of guild [with id] %string%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprRegionOf extends SimpleExpression<String>{
    private Expression<String> vID;
    @Override
    protected String[] get(Event e) {
        return new String[]{getRegionOf(e)};
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
        vID = (Expression<String>) expressions[0];
        return true;
    }
    private String getRegionOf(Event e) {
        try {
            for (Map.Entry<String, JDA> u : bots.entrySet()) {
                if (u.getValue().getGuildById(vID.getSingle(e)) != null) {
                    return u.getValue().getGuildById(vID.getSingle(e)).getRegion().name();
                }
            }

        }catch (NullPointerException x){
            Skript.warning("Could not find a Guild with that ID.");
        }
        return null;
    }
}

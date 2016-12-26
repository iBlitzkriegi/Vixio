package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@ExprAnnotation.Expression(returntype = Member.class, type = ExpressionType.SIMPLE, syntax = "owner of %string%")
public class ExprOwnerOf  extends SimpleExpression<Member>{
    private Expression<Member> vID;
    @Override
    protected Member[] get(Event e) {
        return new Member[]{getOwnerOf(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
    private Member getOwnerOf(Event e) {
        for (Map.Entry<String, JDA> u : bots.entrySet()) {
            for (Guild vG : u.getValue().getGuilds()) {
                return vG.getOwner();
            }
        }
        return null;
    }
}

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
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/6/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "nickname of [user] %string% in [guild] %string%")
public class ExprNicknameOf extends SimpleExpression<String> {
    private Expression<String> vID;
    private Expression<String> vGuild;

    @Override
    protected String[] get(Event e) {
        return new String[]{getNickname(e)};
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
        vGuild = (Expression<String>) expr[1];
        return true;
    }

    private String getNickname(Event e) {
        for (Map.Entry<String, JDA> jdaEntry : bots.entrySet()) {
            if (jdaEntry.getValue().getGuildById(vGuild.getSingle(e)) != null) {
                Guild guild = jdaEntry.getValue().getGuildById(vGuild.getSingle(e));
                Member member = guild.getMember(jdaEntry.getValue().getUserById(vID.getSingle(e)));
                if (member.getNickname() != null) {
                    return member.getNickname();
                } else {
                    return "null";
                }
            } else {
                return "User specified is not in the Guild you specified.";
            }

        }
        return "null";
    }
}
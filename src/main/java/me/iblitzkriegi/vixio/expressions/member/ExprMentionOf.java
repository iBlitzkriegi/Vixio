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
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(
        name = "Mentiontagof",
        title = "Mention tag of",
        desc = "Get the Mention tag of a User/Guild/Channel",
        syntax = "[discord] mention tag of %string%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
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
            if(u.getValue().getUserById(id.getSingle(e))==null) {
                if (u.getValue().getTextChannelById(id.getSingle(e)) == null) {
                    if (u.getValue().getRoleById(id.getSingle(e)) == null) {
                        Skript.warning("Could not find anything with that ID.");
                        return null;
                    } else {
                        return u.getValue().getRoleById(id.getSingle(e)).getAsMention();
                    }
                } else {
                    return u.getValue().getTextChannelById(id.getSingle(e)).getAsMention();
                }
            }else{
                return u.getValue().getUserById(id.getSingle(e)).getAsMention();
            }
        }
        if(e instanceof EvntGuildMemberLeave){
            return ((EvntGuildMemberLeave) e).getEvntUser().getAsMention();
        }
        return null;
    }
}

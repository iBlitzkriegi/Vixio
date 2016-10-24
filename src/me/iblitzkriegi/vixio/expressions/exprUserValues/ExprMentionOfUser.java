package me.iblitzkriegi.vixio.expressions.exprUserValues;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/15/2016.
 */
public class ExprMentionOfUser extends SimpleExpression<String> {
    private Expression<String> user;
    @Override
    protected String[] get(Event e) {
        if (e instanceof EvntGuildMemberLeave) {
            return new String[]{((EvntGuildMemberLeave) e).getEvntUserMention()};
        } else if (e instanceof EvntGuildMemberJoin) {
            String id = ((EvntGuildMemberJoin)e).getEvntUser();
            User user = getAPI().getJDA().getUserById(id);
            return new String[]{user.getAsMention()};
        } else {
            String s = getAPI().getJDA().getUserById(user.getSingle(e)).getAsMention();
            if(s!=null) {
                return new String[]{s};
            }else{
                return new String[]{"Could not find user."};
            }
        }
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
        user = (Expression<String>) expr[0];
        return true;
    }
}


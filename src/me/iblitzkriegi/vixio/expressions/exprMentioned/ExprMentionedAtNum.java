package me.iblitzkriegi.vixio.expressions.exprMentioned;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/21/2016.
 */
public class ExprMentionedAtNum extends SimpleExpression<String> {
    private Expression<Integer> num;
    @Override
    protected String[] get(Event e) {
        Message r = ((EvntGuildMsgReceived)e).getMsgObject();
        if(r.getMentionedUsers().size() > 0){
            User u = r.getMentionedUsers().get(num.getSingle(e));
            return new String[]{u.getId()};
        }else{
            return null;
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
        num = (Expression<Integer>) expr[0];
        return true;
    }
}

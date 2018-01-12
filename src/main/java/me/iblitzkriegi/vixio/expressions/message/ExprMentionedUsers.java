package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 7/27/2017.
 */
public class ExprMentionedUsers extends SimpleExpression<User> {
    static {
        Vixio.getInstance().registerExpression(ExprMentionedUsers.class, User.class, ExpressionType.SIMPLE,
                "mentioned user[s] of %message%")
            .setName("Mentioned users")
            .setDesc("Get the mentioned users in a message")
            .setExample("set {_var::*} to the mentioned users in event-channel");
    }
    private Expression<Message> message;
    @Override
    protected User[] get(Event e) {
        Message message = this.message.getSingle(e);
        if (message == null) {
            return null;
        }
        return message.getMentionedUsers().toArray(new User[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "mention users of " + message.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) exprs[0];
        return true;
    }
}

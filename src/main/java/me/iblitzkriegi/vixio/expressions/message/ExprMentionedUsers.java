package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 7/27/2017.
 */
public class ExprMentionedUsers extends SimpleExpression<User> implements EasyMultiple<UpdatingMessage, User> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionedUsers.class, User.class,
                "mentioned user", "messages")
                .setName("Mentioned Users")
                .setDesc("Get the mentioned Users in a Message")
                .setExample("set {_var::*} to event-message's mentioned users");
    }

    private Expression<UpdatingMessage> messages;

    @Override
    protected User[] get(Event e) {
        return convert(getReturnType(), messages.getAll(e), msg -> {
            Message message = UpdatingMessage.convert(msg);
            List<User> users = message.getMentionedUsers();
            return users.toArray(new User[users.size()]);
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "mentioned users of " + messages.toString(e, debug);
    }

    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<UpdatingMessage>) exprs[0];
        return true;
    }

}

package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 7/27/2017.
 */
public class ExprMentionedRoles extends SimpleExpression<Role> implements EasyMultiple<UpdatingMessage, Role> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionedRoles.class, Role.class,
                "mentioned role", "messages")
                .setName("Mentioned Roles")
                .setDesc("Get the mentioned Roles in a Message")
                .setExample("set {_var::*} to event-message's mentioned roles");
    }

    private Expression<UpdatingMessage> messages;

    @Override
    protected Role[] get(Event e) {
        return convert(getReturnType(), messages.getAll(e), msg -> {
            Message message = UpdatingMessage.convert(msg);
            List<Role> roles = message.getMentionedRoles();
            return roles.toArray(new Role[roles.size()]);
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "mentioned roles of " + messages.toString(e, debug);
    }

    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<UpdatingMessage>) exprs[0];
        return true;
    }

}

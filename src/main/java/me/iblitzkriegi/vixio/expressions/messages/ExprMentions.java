package me.iblitzkriegi.vixio.expressions.messages;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 12/26/2016.
 */
@ExprAnnotation.Expression(
        name = "MentionsOf",
        title = "Mentions in a Message",
        desc = "Get the mentions in a Message, can get one via message with id",
        syntax = "mentions in message %message%",
        returntype = User.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprMentions extends SimpleExpression<User>{
    Expression<Message> vMessage;
    @Override
    protected User[] get(Event e) {
        return getMessageValue(e).toArray(new User[0]);
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
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<Message>) expr[0];
        return true;
    }
    private List<User> getMessageValue(Event e){
        if(vMessage!=null){
            if(vMessage.getSingle(e).getMentionedUsers().size()!=0) {
                return vMessage.getSingle(e).getMentionedUsers();
            }else{
                Skript.warning("Not mentions in that message!");
            }
        }else{
            Skript.warning("Could not find Message.");
        }
        return null;
    }
}

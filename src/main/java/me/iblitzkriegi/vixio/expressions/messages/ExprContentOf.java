package me.iblitzkriegi.vixio.expressions.messages;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/26/2016.
 */
@ExprAnnotation.Expression(
        name = "ContentOf",
        title = "Content of Message",
        desc = "Get the content of a Message, can get one via message with id",
        syntax = "content of message %message%",
        returntype = String.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprContentOf extends SimpleExpression<String>{
    Expression<Message> vMessage;
    @Override
    protected String[] get(Event e) {
        return new String[]{getMessageValue(e)};
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
        vMessage = (Expression<Message>) expr[0];
        return true;
    }
    private String getMessageValue(Event e){
        if(vMessage!=null){
            return vMessage.getSingle(e).getContent();
        }
        return null;
    }
}

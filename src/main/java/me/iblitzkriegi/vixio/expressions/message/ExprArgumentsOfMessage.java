package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprArgumentsOfMessage extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprArgumentsOfMessage.class, List.class, ExpressionType.SIMPLE, "(%message%[']s arguments|[the] vixio arguments of %message%)")
                .setName("Arguments of message")
                .setDesc("Get the arguments of a message split up for you")
                .setExample("set {_var::*} to arguments of event-message");
    }
    private Expression<Message> message;
    @Override
    protected String[] get(Event e) {
        return getArguments(e).toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return message.getAll(event)[0] + "'s arguments";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expressions[0];
        return true;
    }
    private List<String> getArguments(Event e){
        if(message.getSingle(e)!=null){
            List<String> content = new ArrayList<>();
            int i = 0;
            for(String s : message.getSingle(e).getContentDisplay().split(" ")){
                if(i!=0) {
                    content.add(s);
                }
                i++;
            }
            return content;
        }else{
            Skript.error("You must provided a %message%! Refer to the syntax.");
            return null;
        }
    }
}

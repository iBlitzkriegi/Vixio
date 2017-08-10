package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Message;

import java.util.List;

/**
 * Created by Blitz on 7/27/2017.
 */
public class ExprTheMentionedUsers extends SimplePropertyExpression<Object, List> {
    static {
        Vixio.registerPropertyExpression(ExprTheMentionedUsers.class, List.class, "mentioned users", "message");
    }
    @Override
    protected String getPropertyName() {
        return "mentioned users";
    }

    @Override
    public List convert(Object o) {
        if(o instanceof Message){
            return ((Message) o).getMentionedUsers();
        }
        Skript.error("Could not parse provided argument, please refer to the syntax.");
        return null;
    }

    @Override
    public Class<? extends List> getReturnType() {
        return List.class;
    }
}

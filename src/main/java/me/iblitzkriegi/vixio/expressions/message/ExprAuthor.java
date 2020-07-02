package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import net.dv8tion.jda.api.entities.User;

public class ExprAuthor extends SimplePropertyExpression<UpdatingMessage, User> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprAuthor.class, User.class, "[discord] author", "messages")
                .setName("Author of Message")
                .setDesc("Get the author of a message")
                .setExample("author of event-message");
    }

    @Override
    protected String getPropertyName() {
        return "author";
    }

    @Override
    public User convert(UpdatingMessage m) {
        return m.getMessage().getAuthor();
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }
}

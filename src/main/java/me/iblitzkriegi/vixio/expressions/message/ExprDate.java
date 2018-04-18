package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.Message;

public class ExprDate extends SimplePropertyExpression<Message, Date> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprDate.class, Date.class, "(date|creation date)", "messages")
                .setName("Create Date of Message")
                .setDesc("Get the time a message was sent.")
                .setExample("reply with \"%creation date of event-message%\"");
    }

    @Override
    protected String getPropertyName() {
        return "(date|creation date)";
    }

    @Override
    public Date convert(Message message) {
        return Util.getDate(message.getCreationTime());
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }
}

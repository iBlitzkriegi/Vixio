package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import net.dv8tion.jda.api.entities.Message;

public class ExprJumpUrl extends SimplePropertyExpression<UpdatingMessage, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprJumpUrl.class, String.class, "jump url", "message")
                .setName("Jump URL of Message")
                .setDesc("Get the direct jump url to a message.")
                .setExample("");
    }

    @Override
    protected String getPropertyName() {
        return "jump url";
    }

    @Override
    public String convert(UpdatingMessage message) {
        return message.getMessage().getJumpUrl();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

package me.iblitzkriegi.vixio.expressions.message.attachments;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Message;

public class ExprAttachmentUrl extends SimplePropertyExpression<Message.Attachment, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprAttachmentUrl.class, Message.Attachment.class, "attachment url", "attachment")
                .setName("Attachment Url of")
                .setDesc("Get the URL of a message attachment.")
                .setExample("broadcast \"%attachment url of attachment of event-message%\"");
    }
    @Override
    protected String getPropertyName() {
        return "attachment url";
    }

    @Override
    public String convert(Message.Attachment attachment) {
        return attachment.getUrl();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

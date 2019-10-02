package me.iblitzkriegi.vixio.expressions.message.attachments;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Message;

public class ExprAttachmentHeight extends SimplePropertyExpression<Message.Attachment, Number> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprAttachmentHeight.class, Number.class, "attachment height", "attachment")
                .setName("Attachment Height of")
                .setDesc("Get the height of an attachment")
                .setExample("broadcast \"%height of attachment of event-message%\"");
    }

    @Override
    protected String getPropertyName() {
        return "attachment height";
    }

    @Override
    public Number convert(Message.Attachment attachment) {
        if (!attachment.isImage()) {
            return null;
        }
        return attachment.getHeight();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}

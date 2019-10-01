package me.iblitzkriegi.vixio.expressions.message.attachments;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public class ExprAttachment extends SimplePropertyExpression<UpdatingMessage, Message.Attachment> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprAttachment.class, Message.Attachment.class, "attachment", "message")
                .setName("Attachments of")
                .setDesc("Get the attachments of a message.")
                .setExample("broadcast \"%discord name of attachment of event-message%\"");
    }

    @Override
    protected String getPropertyName() {
        return "attachment";
    }

    @Override
    public Message.Attachment convert(UpdatingMessage updatingMessage) {
        List<Message.Attachment> attachmentsList = updatingMessage.getMessage().getAttachments();
        if (attachmentsList.size() != 0) {
            return updatingMessage.getMessage().getAttachments().get(0);
        }
        return null;
    }

    @Override
    public Class<? extends Message.Attachment> getReturnType() {
        return Message.Attachment.class;
    }
}

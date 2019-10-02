package me.iblitzkriegi.vixio.expressions.message.attachments;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Message;

public class ExprAttachmentWidth extends SimplePropertyExpression<Message.Attachment, Number> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprAttachmentWidth.class, Number.class, "attachment width", "attachment")
                .setName("Attachment Width of")
                .setDesc("Get the width of an attachment")
                .setExample(
                        "on guild message received:",
                        "\tset {_} to attachment of event-message",
                        "\tif {_} is set:",
                        "\t\tif {_} is an image:",
                        "\t\t\treply with \"its %attachment width of {_}% by %attachment height of {_}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "attachment width";
    }

    @Override
    public Number convert(Message.Attachment attachment) {
        if (!attachment.isImage()) {
            return null;
        }
        return attachment.getWidth();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}

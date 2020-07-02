package me.iblitzkriegi.vixio.conditions.message;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.event.Event;

public class CondAttachmentIsImage extends Condition implements EasyMultiple<Message.Attachment, Void> {
    static {
        Vixio.getInstance().registerCondition(CondAttachmentIsImage.class, "%attachments% (is|are) [a[n]] image[s]", "%attachments% (is|are)(n't| not) [a[n]] image[s]")
                .setName("Attachment is Image")
                .setDesc("Check if a message attachment is an image.")
                .setExample(
                        "on guild message received:",
                        "\tset {_} to attachment of event-message",
                        "\tif {_} is set:",
                        "\t\tif {_} is not an image:",
                        "\t\t\tbroadcast \"%attachment url of {_}%\""
                );
    }

    private Expression<Message.Attachment> attachments;

    @Override
    public boolean check(Event e) {
        return check(attachments.getAll(e), attachment -> attachment.isImage(), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return attachments.toString(e, debug) + " is " + (isNegated() ? " not " : "") + "an image";
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        attachments = (Expression<Message.Attachment>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}

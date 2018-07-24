package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

public class EffEdit extends Effect {
    static {
        // No need for a bot since you can only edit your own messages
        Vixio.getInstance().registerEffect(EffEdit.class, "edit %messages% to (show|say) %message/string%")
                .setName("Edit Message")
                .setDesc("A more natural way to edit a message. Don't forget that you can only edit your own messages.")
                .setExample(
                        "discord command $edit:",
                        "\ttrigger:",
                        "\t\treply with \"Ping\" and store it in {_msg}",
                        "\t\tedit {_msg} to show \"Pong!\""
                );
    }

    private Expression<UpdatingMessage> messages;
    private Expression<String> content;

    @Override
    protected void execute(Event e) {
        Message content = Util.messageFrom(this.content.getSingle(e));
        if (content == null) {
            return;
        }

        for (Message message : UpdatingMessage.convert(messages.getAll(e))) {
            Bot bot = Util.botFromID(message.getAuthor().getId());
            if (bot != null) {
                Util.bindMessage(bot, message).queue(boundMessage -> {
                    if (boundMessage != null) {
                        boundMessage.editMessage(content).queue();
                    }
                });
            }

        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "edit " + messages.toString(e, debug) + " to say " + content.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<UpdatingMessage>) exprs[0];
        content = (Expression<String>) exprs[1];
        return true;
    }
}
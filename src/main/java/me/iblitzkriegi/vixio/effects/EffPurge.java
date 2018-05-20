package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EffPurge extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffPurge.class, "purge %messages% with %bot/string%")
                .setName("Purge Messages")
                .setDesc("Bulk delete a bunch of messages.")
                .setExample("purge the last grabbed messages with event-bot");
    }

    private Expression<UpdatingMessage> messages;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        if (this.messages == null) {
            return;
        }
        UpdatingMessage[] messages = this.messages.getAll(e);
        List<Message> updatingMessages = Arrays.stream(messages)
                .map(UpdatingMessage::getMessage)
                .collect(Collectors.toList());
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (updatingMessages.isEmpty()) {
            return;
        }
        TextChannel tc = Util.bindChannel(bot, updatingMessages.get(0).getTextChannel());
        if (bot == null || tc == null) {
            return;
        }
        try {
            for(int i = 0; i < updatingMessages.size(); i += 100) {
                List<Message> l = updatingMessages.subList(i, Math.min(updatingMessages.size(), i + 100));
                if(l.size() == 1) {
                    l.get(0).delete().queue();
                } else {
                    tc.deleteMessages(l).queue();
                }
            }
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "bulk delete messages", x.getPermission().getName());
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "purge " + messages.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<UpdatingMessage>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}

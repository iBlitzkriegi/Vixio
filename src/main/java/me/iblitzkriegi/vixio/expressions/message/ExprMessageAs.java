package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/16/2017.
 */
public class ExprMessageAs extends SimpleExpression<Message> {
    static {
        Vixio.getInstance().registerExpression(ExprMessageAs.class, Message.class, ExpressionType.SIMPLE,
                "[message] %messages% with %bot/string%")
                .setName("Message as Bot")
                .setDesc("Used to delete messages mainly")
                .setExample("delete event-message as event-bot");

    }

    private Expression<Message> message;
    private Expression<Object> bot;

    @Override
    protected Message[] get(Event event) {
        Message[] messages = message.getAll(event);
        if (messages == null) {
            return null;
        }
        return messages;
    }

    @Override
    public boolean isSingle() {
        return message.isSingle();
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return message.toString(event, b) + " with " + bot.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expressions[0];
        bot = (Expression<Object>) expressions[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Message[].class};
        }

        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        Message[] messages = message.getAll(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null || messages == null) {
            return;
        }

        for (Message message : messages) {
            try {
                if (Util.botIsConnected(bot, message.getJDA())) {
                    message.delete().queue();
                } else {
                    bot.getJDA().getTextChannelById(message.getChannel().getId()).getMessageById(message.getId()).queue(Message::delete);
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "delete message " + message.getId(), x.getPermission().getName());
            }
        }
    }
}

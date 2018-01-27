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
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Blitz on 8/19/2017.
 */
public class ExprContentOfMessage extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprContentOfMessage.class, String.class, ExpressionType.SIMPLE,
                "content of %messages% [(with|as) %-bot/string%]")
                .setName("Content of Message")
                .setDesc("Get the content of a Message. Must include a bot to modify the content. Changers: SET, DELETE")
                .setExample("content of event-message");
    }

    private Expression<Message> message;
    private Expression<Object> bot;

    @Override
    protected String[] get(Event event) {
        Message[] messages = this.message.getAll(event);
        if (messages != null) {
            return Arrays.stream(messages)
                    .filter(Objects::nonNull)
                    .map(Message::getContentDisplay)
                    .toArray(String[]::new);
        }

        return null;
    }

    @Override
    public boolean isSingle() {
        return message.isSingle();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || message.isSingle())
            return new Class[]{
                    String.class,
                    Message.class
            };

        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("set content of message");
            return;
        }

        String content = mode == Changer.ChangeMode.SET ? (String) delta[0] : null;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Message message = this.message.getSingle(e);
        if (bot == null || message == null) {
            return;
        }

        if (Util.botIsConnected(bot, message.getJDA())) {
            if (content == null) {
                try {
                    message.delete().queue();
                    return;
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "delete message", x.getPermission().getName());
                }
            }
            try {
                message.editMessage(content).queue();
                return;
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "edit message", x.getPermission().getName());
            }

        }

        TextChannel bindedChannel = Util.bindChannel(bot, message.getTextChannel());
        if (bindedChannel == null) {
            return;
        }

        if (content == null) {
            try {
                bindedChannel.getMessageById(message.getId()).queue(message1 -> message1.delete().queue());
                return;
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "delete message", x.getPermission().getName());
            }
        }

        try {
            bindedChannel.getMessageById(message.getId()).queue(message1 -> message1.editMessage(content));
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "edit message", x.getPermission().getName());
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "content of message " + message.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expressions[0];
        bot = (Expression<Object>) expressions[1];
        return true;
    }
}

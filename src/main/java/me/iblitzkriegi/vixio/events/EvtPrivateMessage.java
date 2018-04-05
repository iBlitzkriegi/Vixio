package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class EvtPrivateMessage extends BaseEvent<PrivateMessageReceivedEvent> {

    static {
        BaseEvent.register("private message received", "<receive(d)?( seen)?|sent> [by %-string%]",
                EvtPrivateMessage.class, PrivateMessageEvent.class, "(private message|direct message)")
                .setName("Private Message")
                .setDesc("Fired when a private message is received or sent.")
                .setExample("on direct message received:");

        EventValues.registerEventValue(PrivateMessageEvent.class, Bot.class, new Getter<Bot, PrivateMessageEvent>() {
            @Override
            public Bot get(PrivateMessageEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(PrivateMessageEvent.class, Message.class, new Getter<Message, PrivateMessageEvent>() {
            @Override
            public Message get(PrivateMessageEvent event) {
                return event.getJDAEvent().getMessage();
            }
        }, 0);

        EventValues.registerEventValue(PrivateMessageEvent.class, User.class, new Getter<User, PrivateMessageEvent>() {
            @Override
            public User get(PrivateMessageEvent event) {
                return event.getJDAEvent().getAuthor();
            }
        }, 0);

        EventValues.registerEventValue(PrivateMessageEvent.class, MessageChannel.class, new Getter<MessageChannel, PrivateMessageEvent>() {
            @Override
            public MessageChannel get(PrivateMessageEvent event) {
                return event.getValue(MessageChannel.class);
            }
        }, 0);
    }

    public class PrivateMessageEvent extends SimpleVixioEvent<PrivateMessageReceivedEvent> {
    }

    private boolean sent;

    @Override
    public boolean init(Literal<?>[] exprs, int matchedPattern, SkriptParser.ParseResult parser) {
        sent = parser.regexes.get(0).group().equals("sent");
        return super.init(exprs, matchedPattern, parser);
    }

    @Override
    public boolean check(PrivateMessageReceivedEvent e) {
        if (sent && Util.botFromID(e.getAuthor().getId()) != null) { // if the mode is sent and the author is one of our bots
            return super.check(e);
        } else if (!sent && Util.botFromID(e.getAuthor().getId()) == null) { // if the mode is receive and the author isn't one of our bots
            return super.check(e);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Value[] getValues() {
        return new BaseEvent.Value[] {
                new Value(MessageChannel.class, e -> {
                    try {
                        return e.getAuthor().openPrivateChannel().complete(true);
                    } catch (RateLimitedException e1) {
                        Vixio.getErrorHandler().warn("Vixio tried to get the message event value for the reaction add event but was rate limited");
                        return null;
                    } catch (UnsupportedOperationException x) {
                        return null;
                    }
                })
        };
    }
}

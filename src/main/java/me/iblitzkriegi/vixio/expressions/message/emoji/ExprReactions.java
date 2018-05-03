package me.iblitzkriegi.vixio.expressions.message.emoji;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimpleExpression;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprReactions extends ChangeableSimpleExpression<Emote> implements EasyMultiple<UpdatingMessage, Emote> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprReactions.class, Emote.class,
                "reactions", "messages")
                .setName("Reactions of message")
                .setDesc("Get the reactions of a message. Can be deleted, reset, removed and added to.")
                .setExample(
                        "on guild message receive:",
                        "\tadd reactions \"smile\" and \"frowning\" to reactions of event-message"
                );
    }

    private Expression<UpdatingMessage> messages;

    @Override
    protected Emote[] get(Event e) {
        return convert(getReturnType(), messages.getAll(e), msg -> {
            Message message = UpdatingMessage.convert(msg);
            List<Emote> emojis = new ArrayList<>();
            for (MessageReaction messageReaction : message.getReactions()) {
                String name = messageReaction.getReactionEmote().getName();
                if (messageReaction.getReactionEmote().getEmote() == null) {
                    emojis.add(Util.unicodeFrom(name));
                } else {
                    emojis.add(new Emote(messageReaction.getReactionEmote().getEmote()));
                }
            }

            return emojis.toArray(new Emote[emojis.size()]);
        });
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Emote> getReturnType() {
        return Emote.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the reactions of " + messages.toString(e, debug);

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<UpdatingMessage>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.REMOVE_ALL ||
                mode == Changer.ChangeMode.DELETE ||
                mode == Changer.ChangeMode.RESET) {
            return new Class[]{Emote[].class};
        }

        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        change(messages.getAll(e), msg -> {
            Message message = UpdatingMessage.convert(msg);
            TextChannel channel = Util.bindChannel(bot, message.getTextChannel());
            //TODO: use Util#bindMessage to bind the message and remove botIsConnected checks after this
            if (channel == null) {
                return;
            }

            switch (mode) {
                case ADD:
                    try {
                        if (Util.botIsConnected(bot, message.getJDA())) {
                            for (Object o : delta) {
                                try {
                                    Emote emoji = (Emote) o;
                                    if (Util.botIsConnected(bot, message.getJDA())) {
                                        if (emoji.isEmote()) {
                                            message.addReaction(emoji.getEmote()).queue();
                                        } else {
                                            message.addReaction(emoji.getName()).queue();
                                        }
                                    } else {
                                        if (emoji.isEmote()) {
                                            channel.addReactionById(message.getId(), emoji.getEmote()).queue();
                                        } else {
                                            channel.addReactionById(message.getId(), emoji.getName()).queue();
                                        }
                                    }
                                } catch (IllegalArgumentException x) {
                                    Vixio.getErrorHandler().warn("Vixio attempted to add a emote to " + message.getId() + " with " + bot.getName() + " but was unable to find the emoji.");
                                }
                            }
                        }
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "add emoji", x.getPermission().getName());
                    }
                    break;
                case DELETE:
                case RESET:
                    try {
                        if (Util.botIsConnected(bot, message.getJDA())) {
                            message.clearReactions().queue();
                        } else {
                            channel.getMessageById(message.getId()).queue(m -> m.clearReactions().queue());
                        }
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "remove all emojis", x.getPermission().getName());
                    }
                    break;
                case REMOVE_ALL:
                case REMOVE:
                    try {
                        for (Object o : delta) {
                            Emote emoji = (Emote) o;
                            if (Util.botIsConnected(bot, message.getJDA())) {
                                for (MessageReaction messageReaction : message.getReactions()) {
                                    if (messageReaction.getReactionEmote().getName().equals(emoji.getName())) {
                                        for (User user : messageReaction.getUsers()) {
                                            messageReaction.removeReaction(user).queue();
                                        }
                                    }
                                }
                                return;
                            }
                        }
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "remove emoji", x.getPermission().getName());
                    }
                    break;

            }
        });
    }


}

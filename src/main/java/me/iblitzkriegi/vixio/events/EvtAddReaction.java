package me.iblitzkriegi.vixio.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

public class EvtAddReaction extends BaseEvent<MessageReactionAddEvent> {

    static {
        BaseEvent.register("reaction added", EvtAddReaction.class, ReactionAddEvent.class,
                "reaction add[ed]")
                .setName("Reaction Add")
                .setDesc("Fired when a reaction is added to a message")
                .setExample("on reaction add:");



        EventValues.registerEventValue(ReactionAddEvent.class, Bot.class, new Getter<Bot, ReactionAddEvent>() {
            @Override
            public Bot get(ReactionAddEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, User.class, new Getter<User, ReactionAddEvent>() {
            @Override
            public User get(ReactionAddEvent event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, Member.class, new Getter<Member, ReactionAddEvent>() {
            @Override
            public Member get(ReactionAddEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, Guild.class, new Getter<Guild, ReactionAddEvent>() {
            @Override
            public Guild get(ReactionAddEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, UpdatingMessage.class, new Getter<UpdatingMessage, ReactionAddEvent>() {
            @Override
            public UpdatingMessage get(ReactionAddEvent e) {
                return UpdatingMessage.from(e.getValue(Message.class));
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, MessageChannel.class, new Getter<MessageChannel, ReactionAddEvent>() {
            @Override
            public MessageChannel get(ReactionAddEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, GuildChannel.class, new Getter<GuildChannel, ReactionAddEvent>() {
            @Override
            public GuildChannel get(ReactionAddEvent event) {
                return event.getJDAEvent().getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, Emote.class, new Getter<Emote, ReactionAddEvent>() {
            @Override
            public Emote get(ReactionAddEvent event) {
                net.dv8tion.jda.api.entities.Emote emote = event.getJDAEvent().getReactionEmote().getEmote();
                if (emote == null) {
                    return Util.unicodeFrom(event.getJDAEvent().getReactionEmote().getName());
                } else {
                    return new Emote(emote);
                }
            }
        }, 0);

    }

    public class ReactionAddEvent extends SimpleVixioEvent<MessageReactionAddEvent> {
    }

    @Override
    @SuppressWarnings("unchecked")
    public Value[] getValues() {
        return new BaseEvent.Value[] {
                new Value(Message.class, e -> {
                    try {
                        return e.getChannel().retrieveMessageById(e.getMessageId()).complete(true);
                    } catch (RateLimitedException e1) {
                        Vixio.getErrorHandler().warn("Vixio tried to get the message event value for the reaction add event but was rate limited");
                        return null;
                    }
                })
        };
    }

}

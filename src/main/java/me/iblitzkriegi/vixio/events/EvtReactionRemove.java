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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

public class EvtReactionRemove extends BaseEvent<MessageReactionRemoveEvent> {

    static {
        BaseEvent.register("reaction remove[d]", EvtReactionRemove.class, ReactionRemoveEvent.class,
                "reaction remove[d]")
                .setName("Reaction Remove")
                .setDesc("Fired when a reaction is removed from a message")
                .setExample("on reaction remove:");



        EventValues.registerEventValue(ReactionRemoveEvent.class, Bot.class, new Getter<Bot, ReactionRemoveEvent>() {
            @Override
            public Bot get(ReactionRemoveEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, User.class, new Getter<User, ReactionRemoveEvent>() {
            @Override
            public User get(ReactionRemoveEvent event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, Member.class, new Getter<Member, ReactionRemoveEvent>() {
            @Override
            public Member get(ReactionRemoveEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, Guild.class, new Getter<Guild, ReactionRemoveEvent>() {
            @Override
            public Guild get(ReactionRemoveEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, UpdatingMessage.class, new Getter<UpdatingMessage, ReactionRemoveEvent>() {
            @Override
            public UpdatingMessage get(ReactionRemoveEvent e) {
                return UpdatingMessage.from(e.getValue(Message.class));
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, MessageChannel.class, new Getter<MessageChannel, ReactionRemoveEvent>() {
            @Override
            public MessageChannel get(ReactionRemoveEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, GuildChannel.class, new Getter<GuildChannel, ReactionRemoveEvent>() {
            @Override
            public GuildChannel get(ReactionRemoveEvent event) {
                return event.getJDAEvent().getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(ReactionRemoveEvent.class, Emote.class, new Getter<Emote, ReactionRemoveEvent>() {
                @Override
                public Emote get(ReactionRemoveEvent event) {
                    MessageReaction.ReactionEmote reactionEmote = event.getJDAEvent().getReactionEmote();
                    if (!reactionEmote.isEmote()) {
                        return Util.unicodeFrom(reactionEmote.getName());
                    } else {
                        return new Emote(reactionEmote.getEmote());
                    }
                }
            }, 0);

    }

    public class ReactionRemoveEvent extends SimpleVixioEvent<MessageReactionRemoveEvent> {
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

package me.iblitzkriegi.vixio.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class EvtAddReaction extends BaseEvent<MessageReactionAddEvent> {

    // TODO: add event values
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
                return event.getJDAEvent().getMember().getUser();
            }
        }, 0);

        EventValues.registerEventValue(ReactionAddEvent.class, Member.class, new Getter<Member, ReactionAddEvent>() {
            @Override
            public Member get(ReactionAddEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

    }

    public class ReactionAddEvent extends SimpleVixioEvent<MessageReactionAddEvent> {
    }

}

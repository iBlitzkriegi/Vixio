package me.iblitzkriegi.vixio.events.message;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EvtMessageReceived extends BaseEvent<MessageReceivedEvent> {
    static {
        BaseEvent.register("message received", EvtMessageReceived.class, GlobalMessageReceived.class,
                "message receive[d]")
                .setName("Message Received")
                .setDesc("Fired when a message is received anywhere, either a private message or a text channel")
                .setExample("on message received:");

        EventValues.registerEventValue(GlobalMessageReceived.class, User.class, new Getter<User, GlobalMessageReceived>() {
            @Override
            public User get(GlobalMessageReceived event) {
                return event.getJDAEvent().getAuthor();
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, MessageChannel.class, new Getter<MessageChannel, GlobalMessageReceived>() {
            @Override
            public MessageChannel get(GlobalMessageReceived event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, Member.class, new Getter<Member, GlobalMessageReceived>() {
            @Override
            public Member get(GlobalMessageReceived event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, Bot.class, new Getter<Bot, GlobalMessageReceived>() {
            @Override
            public Bot get(GlobalMessageReceived event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, String.class, new Getter<String, GlobalMessageReceived>() {
            @Override
            public String get(GlobalMessageReceived event) {
                return event.getJDAEvent().getMessage().getContentRaw();
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, UpdatingMessage.class, new Getter<UpdatingMessage, GlobalMessageReceived>() {
            @Override
            public UpdatingMessage get(GlobalMessageReceived event) {
                return UpdatingMessage.from(event.getJDAEvent().getMessage());
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, Guild.class, new Getter<Guild, GlobalMessageReceived>() {
            @Override
            public Guild get(GlobalMessageReceived event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(GlobalMessageReceived.class, GuildChannel.class, new Getter<GuildChannel, GlobalMessageReceived>() {
            @Override
            public GuildChannel get(GlobalMessageReceived event) {
                return event.getJDAEvent().getTextChannel();
            }
        }, 0);

    }

    public class GlobalMessageReceived extends SimpleVixioEvent<MessageReceivedEvent> {
    }

}

package me.iblitzkriegi.vixio.events.message;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;

public class EvtDeleteMessage extends BaseEvent<MessageDeleteEvent> {
    static {
        BaseEvent.register("message delete[d]", EvtDeleteMessage.class, DeleteMessageEvent.class,
                "message delete[d]")
                .setName("Message Deleted")
                .setDesc("Fired when a message is deleted")
                .setExample("on message deleted");

        EventValues.registerEventValue(DeleteMessageEvent.class, Bot.class, new Getter<Bot, DeleteMessageEvent>() {
            @Override
            public Bot get(DeleteMessageEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(DeleteMessageEvent.class, UpdatingMessage.class, new Getter<UpdatingMessage, DeleteMessageEvent>() {
            @Override
            public UpdatingMessage get(DeleteMessageEvent event) {
                return UpdatingMessage.from(event.getJDAEvent().getMessageId());
            }
        }, 0);

        EventValues.registerEventValue(DeleteMessageEvent.class, MessageChannel.class, new Getter<MessageChannel, DeleteMessageEvent>() {
            @Override
            public MessageChannel get(DeleteMessageEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(DeleteMessageEvent.class, GuildChannel.class, new Getter<GuildChannel, DeleteMessageEvent>() {
            @Override
            public GuildChannel get(DeleteMessageEvent event) {
                return event.getJDAEvent().getTextChannel();
            }
        }, 0);

        EventValues.registerEventValue(DeleteMessageEvent.class, Guild.class, new Getter<Guild, DeleteMessageEvent>() {
            @Override
            public Guild get(DeleteMessageEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

    }

    public class DeleteMessageEvent extends SimpleVixioEvent<MessageDeleteEvent> {

    }
}

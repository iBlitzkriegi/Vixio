package me.iblitzkriegi.vixio.util;

import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageUpdater extends ListenerAdapter {

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        if (shouldUpdate(e.getMessageId())) {
            UpdatingMessage.put(e.getMessageId(), e.getMessage());
        }
    }

    @Override
    public void onGenericMessageReaction(GenericMessageReactionEvent e) {
        if (shouldUpdate(e.getMessageId())) {
            e.getChannel().retrieveMessageById(e.getMessageId())
                    .queue(message -> UpdatingMessage.put(e.getMessageId(), message));
        }
    }

    public boolean shouldUpdate(String ID) {
        UpdatingMessage message = UpdatingMessage.getUpdatingMessage(ID);
        if (message == null) {
            return false;
        }
        return !message.isPaused();
    }
}

package me.iblitzkriegi.vixio.util;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.Validate;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;
import org.bukkit.plugin.messaging.Messenger;
import org.eclipse.jdt.annotation.NonNull;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class UpdatingMessage {

	private static final Map<String, Message> MESSAGE_MAP = new HashMap<>();
	// it's important to use a weakreference to prevent a memory leak here
	private static final Map<String, WeakReference<UpdatingMessage>> UPDATING_MESSAGES = new HashMap<>();

	public static Message[] convert(UpdatingMessage[] updatingMessages) {
		if (updatingMessages == null) {
			return new Message[0];
		}
		List<Message> messages = new ArrayList<>();
		for (UpdatingMessage updatingMessage : updatingMessages) {
			Message message = convert(updatingMessage);
			if (message != null) {
				messages.add(message);
			}
		}
		return messages.toArray(new Message[0]);
	}

	public static Message convert(UpdatingMessage updatingMessage) {
		if (updatingMessage == null) {
			return null;
		}
		return updatingMessage.getMessage();
	}

	public static UpdatingMessage from(@NonNull Message message) {
		Validate.notNull(message);
		if (MESSAGE_MAP.get(message.getId()) == null) {
			return new UpdatingMessage(message);
		}
		// this shouldn't ever cause an npe. something is bad if it did
		return UPDATING_MESSAGES.get(message.getId()).get();
	}

	public static void put(String ID, Message message) {
		Validate.notNull(ID, message);
		MESSAGE_MAP.put(ID, message);
	}

	public static boolean exists(String ID) {
		return UPDATING_MESSAGES.get(ID) != null;
	}

	public static UpdatingMessage getUpdatingMessage(String ID) {
		WeakReference<UpdatingMessage> message = UPDATING_MESSAGES.get(ID);
		return message == null ? null : message.get();
	}

	private String ID;
	private boolean paused;

	private UpdatingMessage(@NonNull Message message) {
		Validate.notNull(message);
		this.ID = message.getId();
		MESSAGE_MAP.put(ID, message);
		UPDATING_MESSAGES.put(ID, new WeakReference<>(this));
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void pause() {
		setPaused(true);
	}

	public void resume() {
		setPaused(false);
	}

	public String getID() {
		return ID;
	}

	public Message getMessage() {
		return MESSAGE_MAP.get(ID);
	}

	@Override
	protected void finalize() throws Throwable {
		// we don't need the message anymore if the updatingmessage was GCd
		MESSAGE_MAP.remove(getID());
	}

}

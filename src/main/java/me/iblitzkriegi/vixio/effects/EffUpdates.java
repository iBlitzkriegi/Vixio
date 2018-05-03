package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

public class EffUpdates extends Effect {

	static {
		Vixio.getInstance().registerEffect(EffUpdates.class, "<pause|resume> updates for %messages%")
				.setName("Updates")
				.setDesc("Pauses or resumes update's to a message's data. For example, if updates are paused" +
						"and a new reaction is added to the paused messages, the message will still show" +
						"as if it doesn't have that reaction.")
				.setExample("on guild message receive:",
							"\tpause updates for event-message"
				);
	}

	private boolean pause;
	private Expression<UpdatingMessage> messages;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		messages = (Expression<UpdatingMessage>) exprs[0];
		pause = parseResult.regexes.get(0).group().equals("pause");
		return true;
	}

	@Override
	protected void execute(Event e) {
		for (UpdatingMessage message : messages.getAll(e)) {
			message.setPaused(pause);
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return (pause ? "pause" : "resume") + " updates for" + messages.toString(e, debug);
	}

}

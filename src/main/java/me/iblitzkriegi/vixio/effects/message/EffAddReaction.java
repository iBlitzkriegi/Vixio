package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffAddReaction extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffAddReaction.class, "add %emotes% to %messages% [with %bot/string%]")
                .setName("Add Reaction to Message")
                .setDesc("Add a reaction to a message, can get a reaction with the \"reaction %string%\" expression")
                .setExample("add reaction \"smile\" to event-message with \"Jewel\"");
    }

    private Expression<Message> message;
    private Expression<Emote> emote;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }

        for (Message message : this.message.getAll(e)) {
            Message bindedMessage = Util.bindMessage(bot, message);
            if (bindedMessage != null) {
                for (Emote emote : this.emote.getAll(e)) {
                    try {
                        if (emote.isEmote()) {
                            message.addReaction(emote.getEmote()).queue();
                        } else {
                            message.addReaction(emote.getAsMention()).queue();
                        }
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "add reaction", x.getPermission().getName());
                    }
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add " + emote.toString(e, debug) + " to " + message.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        emote = (Expression<Emote>) exprs[0];
        message = (Expression<Message>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }
}

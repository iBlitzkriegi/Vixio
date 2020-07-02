package me.iblitzkriegi.vixio.effects.channel;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffPinMessage extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffPinMessage.class, "pin %messages% [with %bot/string%]")
                .setName("Pin Message")
                .setDesc("Pin a message in a channel.")
                .setExample("pin event-message in event-channel");
    }

    private Expression<UpdatingMessage> message;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        UpdatingMessage updatingMessage = this.message.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (updatingMessage == null || bot == null) {
            return;
        }
        Message message = updatingMessage.getMessage();
        MessageChannel channel = Util.bindMessageChannel(bot, message.getChannel());
        if (channel == null) {
            return;
        }
        try {
            channel.pinMessageById(message.getId()).queue();
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "pin message", x.getPermission().getName());
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "pin " + message.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<UpdatingMessage>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}

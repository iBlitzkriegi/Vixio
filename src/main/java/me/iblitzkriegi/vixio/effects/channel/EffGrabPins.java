package me.iblitzkriegi.vixio.effects.channel;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.channel.ExprChannelPinned;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffGrabPins extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffGrabPins.class, "(retrieve|grab) pinned messages (of|in|with) %textchannel/channel/user% [with %bot/string%]")
                .setName("Garb Pinned Messages")
                .setDesc("Grab the pinned messages of a channel or a dm with a user. Can be gotten with the last grabbed pinned messages expression.")
                .setUserFacing("(retrieve|grab) pinned messages (of|in|with) %textchannel/user%")
                .setExample("grab pinned messages of event-channel");
    }

    private Expression<Object> source;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Object source = this.source.getSingle(e);
        MessageChannel channel = Util.getMessageChannel(bot, source);
        if (channel != null) {
            try {
                ExprChannelPinned.pinnedMessages = null;
                ExprChannelPinned.pinnedMessages = channel.getPinnedMessages().complete(true);
            } catch (RateLimitedException e1) {
                Vixio.getErrorHandler().warn("Vixio attempted to retrieve pinned messages but was ratelimited");
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grab pinned messages of " + source.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        source = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.bukkit.event.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EffGrabMessages extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffGrabMessages.class, "grab [the] last %number% messages in %textchannel/channel%")
                .setName("Grab Messages")
                .setDesc("Grab a number of messages from a text channel")
                .setUserFacing("grab [the] last %number% messages in %textchannel%")
                .setExample("grab the last 5 messages in event-channel");
    }

    private Expression<Number> messages;
    private Expression<Channel> channel;
    public static List<UpdatingMessage> updatingMessages;

    @Override
    protected void execute(Event e) {
        Number messages = this.messages.getSingle(e);
        Channel channel = this.channel.getSingle(e);
        if (messages == null || !(channel instanceof MessageChannel)) {
            return;
        }
        updatingMessages = ((MessageChannel) channel).getIterableHistory().stream()
                .limit(messages.intValue())
                .map(UpdatingMessage::from)
                .collect(Collectors.toList());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grab the last " + messages.toString(e, debug) + " messages in " + channel.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<Number>) exprs[0];
        channel = (Expression<Channel>) exprs[1];
        return true;
    }
}

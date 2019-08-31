package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 7/27/2017.
 */
public class ExprMentionedChannels extends SimpleExpression<TextChannel> implements EasyMultiple<UpdatingMessage, TextChannel> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionedChannels.class, TextChannel.class,
                "mentioned channel", "messages")
                .setName("Mentioned Channels")
                .setDesc("Get the mentioned Channels in a Message")
                .setExample("set {_var::*} to event-message's mentioned channels");
    }

    private Expression<UpdatingMessage> messages;

    @Override
    protected TextChannel[] get(Event e) {
        return convert(getReturnType(), messages.getAll(e), msg -> {
            Message message = UpdatingMessage.convert(msg);
            List<TextChannel> textChannels = message.getMentionedChannels();
            return textChannels.toArray(new TextChannel[textChannels.size()]);
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "mentioned channels of " + messages.toString(e, debug);
    }

    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends TextChannel> getReturnType() {
        return TextChannel.class;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<UpdatingMessage>) exprs[0];
        return true;
    }

}

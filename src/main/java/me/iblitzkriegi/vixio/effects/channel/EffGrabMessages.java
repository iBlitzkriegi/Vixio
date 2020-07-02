package me.iblitzkriegi.vixio.effects.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.event.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EffGrabMessages extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffGrabMessages.class, "grab [the] last %number% messages in %textchannel/channel% [and store (them|the messages|it) in %-objects%]")
                .setName("Grab Messages")
                .setDesc("Grab a number of messages from a text channel")
                .setUserFacing("grab [the] last %number% messages in %textchannel%")
                .setExample(
                        "discord command $purge <number>:",
                        "\texecutable in: guild",
                        "\ttrigger:",
                        "\t\tset {_num} to arg-1 ",
                        "\t\tgrab the last {_num} messages in event-channel",
                        "\t\tpurge the grabbed messages with event-bot",
                        "\t\tset {_error} to last vixio error ",
                        "\t\tif {_error} is set:",
                        "\t\t\treply with \"I ran into an error! `%{_error}%`\"",
                        "\t\t\tstop",
                        "\t\treply with \"I have successfully purged %arg-1% messages\""
                );
    }

    private Expression<Number> messages;
    private Expression<GuildChannel> channel;
    public static List<UpdatingMessage> updatedMessages;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        Number messages = this.messages.getSingle(e);
        GuildChannel channel = this.channel.getSingle(e);
        if (messages == null || !(channel instanceof MessageChannel)) {
            return;
        }
        List<UpdatingMessage> updatingMessages = ((MessageChannel) channel).getIterableHistory().stream()
                .limit(messages.intValue())
                .map(UpdatingMessage::from)
                .collect(Collectors.toList());
        updatedMessages = updatingMessages;
        if (varExpr != null) {
            Util.storeInVar(varName, varExpr, updatingMessages, e);
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grab the last " + messages.toString(e, debug) + " messages in " + channel.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<Number>) exprs[0];
        channel = (Expression<GuildChannel>) exprs[1];
        if (exprs[2] instanceof Variable) {
            varExpr = (Variable<?>) exprs[2];
            varName = SkriptUtil.getVariableName(varExpr);
        }
        return true;
    }
}

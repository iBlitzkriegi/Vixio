package me.iblitzkriegi.vixio.effects.message;

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
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffSendMessage extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffSendMessage.class, "send %messages/strings% to %users/channels% with %bot/string% [and store (it|the message) in %-objects%]")
                .setName("Send Message")
                .setDesc("Send a message to either a user or a text channel.")
                .setUserFacing("send %message/string/messagebuilder/embedbuilder% to %channels/users% with %bot/string% [and store (it|the message) in %-objects%]")
                .setExample(
                        "discord command $send <text> [<text>]:",
                        "\ttrigger:",
                        "\t\tif arg-2 is not set:",
                        "\t\t\tsend arg-1 to event-channel with event-bot",
                        "\t\t\tstop",
                        "\t\tset {_channel} to channel with id arg-2",
                        "\t\tif {_channel} is not set:",
                        "\t\t\treply with \"I could not find a channel with that id!\"",
                        "\t\t\tstop",
                        "\t\tsend arg-1 to {_channel} with event-bot"
                );
    }

    private Expression<Object> message;
    private Expression<Object> sources;
    private Expression<Object> bot;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Message message = Util.messageFrom(this.message.getSingle(e));
        if (bot == null || message == null) {
            return;
        }
        try {
            for (Object source : sources.getArray(e)) {
                MessageChannel messageChannel = Util.getMessageChannel(bot, source);
                if (messageChannel != null) {
                    if (varExpr == null) {
                        messageChannel.sendMessage(message).queue();
                    } else {
                        Message resultingMessage = messageChannel.sendMessage(message).complete(true);
                        if (resultingMessage != null) {
                            Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                        }
                    }
                }
            }
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "send message", x.getPermission().getName());
        } catch (RateLimitedException e1) {
            Vixio.getErrorHandler().cantOpenPrivateChannel();
        } catch (Exception x ){
            Vixio.getErrorHandler().cantOpenPrivateChannel();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + message.toString(e, debug) + " to " + sources.toString(e, debug) + " with " + bot.toString(e, debug) + (varExpr == null ? "" : ("and store it in " + varExpr.toString(e, debug)));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<Object>) exprs[0];
        sources = (Expression<Object>) exprs[1];
        bot = (Expression<Object>) exprs[2];

        if (exprs[3] instanceof Variable) {
            varExpr = (Variable<?>) exprs[3];
            varName = SkriptUtil.getVariableName(varExpr);
        }

        return true;
    }
}

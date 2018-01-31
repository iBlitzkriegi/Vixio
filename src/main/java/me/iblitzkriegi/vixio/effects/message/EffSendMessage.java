package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.AsyncEffect;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffSendMessage extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffSendMessage.class, "send %messages/strings% to %channels% [(with|as) %bot/string%] [and store (it|the message) in %-objects%]")
                .setName("Send Message to Text Channel")
                .setDesc("Send a Message to a Text Channel.")
                .setExample("send \"hey\" to channel with id \"156156165165156\" as \"Jewel\"", "send \"hey\" to channel with id \"156156165165156\" as \"Jewel\" and store it in {_message}")
                .setUserFacing("send %message/string/messagebuilder/embedbuilder% to %channels% with %bot/string% [and store (it|the message) in %-objects%]");
    }

    private Expression<Object> message;
    private Expression<Channel> channel;
    private Expression<Object> bot;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }
        try {
            if (bot.getJDA() != null) {
                for (Channel channel : channel.getAll(e)) {
                    if (channel.getType().equals(ChannelType.TEXT)) {
                        TextChannel textChannel = Util.botIsConnected(bot, channel.getJDA()) ?
                                (TextChannel) channel : bot.getJDA().getTextChannelById(channel.getId());

                        for (Object m : message.getAll(e)) {
                            Message message = Util.messageFrom(m);
                            if (message != null) {

                                if (varExpr == null) {
                                    textChannel.sendMessage(message).queue();
                                } else {

                                    try {
                                        Message resultingMessage = textChannel.sendMessage(message).complete(true);
                                        if (varExpr.isList()) {
                                            Util.setList(varName.toString(e), e, varExpr.isLocal(), resultingMessage);
                                        } else {
                                            Variables.setVariable(varName.toString(e), resultingMessage, e, varExpr.isLocal());
                                        }
                                    } catch (RateLimitedException e1) {
                                        Vixio.getErrorHandler().warn("Vixio tried to send and store a message but was rate limited");
                                    }

                                }

                            }
                        }

                    }
                }
            }
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "send message");
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + message.toString(e, debug) + " to " + channel.toString(e, debug) + " with " + bot.toString(e, debug) + ("and store it in " + varExpr.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<Object>) exprs[0];
        channel = (Expression<Channel>) exprs[1];
        bot = (Expression<Object>) exprs[2];

        if (exprs[3] instanceof Variable) {
            varExpr = (Variable<?>) exprs[3];
            varName = Util.getVariableName(varExpr);
        }

        return true;
    }
}

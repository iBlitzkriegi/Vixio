package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffSendMessage extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffSendMessage.class, "send %messages/strings% to %channels/users% [with %bot/string%] [and store (it|the message) in %-objects%]")
                .setName("Send Message to Text Channel")
                .setDesc("Send a message to a text channel.")
                .setExample("send \"hey\" to channel with id \"156156165165156\" as \"Jewel\"", "send \"hey\" to channel with id \"156156165165156\" as \"Jewel\" and store it in {_message}")
                .setUserFacing("send %message/string/messagebuilder/embedbuilder% to %channels% with %bot/string% [and store (it|the message) in %-objects%]");
    }

    private Expression<Object> message;
    private Expression<Object> sources;
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
            for (Object source : sources.getAll(e)) {
                if (source instanceof TextChannel) {
                    TextChannel textChannel = Util.bindChannel(bot, (TextChannel) source);
                    if (textChannel != null) {
                        for (Object m : message.getAll(e)) {
                            Message message = Util.messageFrom(m);
                            if (message != null) {
                                if (varExpr == null) {
                                    textChannel.sendMessage(message).queue();
                                } else {
                                    try {
                                        Message resultingMessage = textChannel.sendMessage(message).complete(true);
                                        if (varExpr.isList()) {
                                            SkriptUtil.setList(varName.toString(e), e, varExpr.isLocal(), resultingMessage);
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
                } else if (source instanceof User) {
                    User user = Util.bindUser(bot, (User) source);
                    if (user != null) {
                        for (Object m : message.getAll(e)) {
                            Message message = Util.messageFrom(m);
                            if (message != null) {
                                if (varExpr == null) {
                                    user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(message).queue());
                                } else {
                                    try {
                                        Message resultingMessage = user.openPrivateChannel().complete(true).sendMessage(message).complete(true);
                                        if (varExpr.isList()) {
                                            SkriptUtil.setList(varName.toString(e), e, varExpr.isLocal(), resultingMessage);
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
            Vixio.getErrorHandler().needsPerm(bot, "send message", x.getPermission().getName());
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

package me.iblitzkriegi.vixio.effects;

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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.io.File;
import java.io.InputStream;

public class EffUploadFile extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffUploadFile.class, "upload %string% [with (message|embed) %-message/string%] to %user/channel% [with %bot/string%] [and store (it|the message) in %-objects%]")
                .setName("Send file")
                .setDesc("Send a file to a channel or a user. You can input a direct URL to the file or a direct path to the local image/file.")
                .setExample(
                        "discord command $upload [<text>] [<text>]:",
                        "\ttrigger:",
                        "\t\tif arg-1 is not set:",
                        "\t\t\tupload \"https://cdn.discordapp.com/attachments/236641445363056651/482328479288000513/e8873489-b8e8-41f0-bfdf-1af0e7f8689a.png\" to event-channel",
                        "\t\t\tstop",
                        "\t\tif arg-2 is not set:",
                        "\t\t\tupload arg-1 to event-channel",
                        "\t\t\tstop",
                        "\t\tupload arg-1 with message arg-2 to event-channel"
                );
    }

    private Expression<String> file;
    private Expression<Object> message;
    private Expression<Object> channel;
    private Expression<Object> bot;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        String file = this.file.getSingle(e);
        Message message = Util.messageFrom(this.message == null ? null : this.message.getSingle(e));
        Object channel = this.channel.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (file == null || channel == null || bot == null) {
            return;
        }
        MessageChannel messageChannel = null;
        if (channel instanceof User) {
            try {
                messageChannel = ((User) channel).openPrivateChannel().complete(true);
            } catch (RateLimitedException e1) {
                Vixio.getErrorHandler().warn("Vixio attempted to open a private channel but was ratelimited");
                return;
            }
        } else if (channel instanceof GuildChannel) {
            if (channel instanceof TextChannel) {
                messageChannel = (MessageChannel) channel;
            }
        }
        MessageChannel boundChannel = Util.bindMessageChannel(bot, messageChannel);
        if (boundChannel == null) {
            return;
        }
        if (Util.isLink(file)) {
            InputStream inputStream = Util.getInputStreamFromUrl(file);
            String extension = Util.getExtensionFromUrl(file);
            if (message != null) {
                if (varExpr != null) {
                    Message resultingMessage = boundChannel.sendMessage(message)
                            .addFile(inputStream, "file." + extension).complete();
                    Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                } else {
                    boundChannel.sendMessage(message)
                            .addFile(inputStream, "file." + extension).queue();
                }
            } else {
                if (varExpr != null) {
                    Message resultingMessage = boundChannel.sendFile(inputStream, "file." + extension).complete();
                    Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);

                } else {
                    boundChannel.sendFile(inputStream, "file." + extension).queue();
                }
            }

        } else {
            File toSend = new File(file);
            if (toSend.exists()) {
                if (message != null) {
                    if (varExpr != null) {
                        Message resultingMessage = boundChannel.sendMessage(message)
                                .addFile(toSend).complete();
                        Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                    } else {
                        boundChannel.sendMessage(message)
                                .addFile(toSend).queue();
                    }
                } else {
                    if (varExpr != null) {
                        Message resultingMessage = boundChannel.sendFile(toSend).complete();
                        Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                    } else {
                        boundChannel.sendFile(toSend).queue();
                    }
                }
            }
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + file.toString(e, debug) + (message == null ? "" : " with message " + message.toString(e, debug) + " to " + channel.toString(e, debug) + " with " + bot.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        file = (Expression<String>) exprs[0];
        message = (Expression<Object>) exprs[1];
        channel = (Expression<Object>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        if (exprs[4] instanceof Variable) {
            varExpr = (Variable<?>) exprs[4];
            varName = SkriptUtil.getVariableName(varExpr);
        }
        return true;
    }
}

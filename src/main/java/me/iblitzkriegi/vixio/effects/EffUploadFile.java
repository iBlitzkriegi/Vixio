package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class EffUploadFile extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffUploadFile.class, "(send|upload) %string% [with (message|embed) %-message/string%] to %user/channel% [with %bot/string%]")
                .setName("Send file")
                .setDesc("Send a file to a channel or a user. You can input a direct URL to the file or a direct path to the local image/file.")
                .setExample("send \"https://i.imgflip.com/27yc0d.jpg\" to event-channel");
    }

    private Expression<String> file;
    private Expression<Object> message;
    private Expression<Object> channel;
    private Expression<Object> bot;

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
        } else if (channel instanceof Channel) {
            if (channel instanceof TextChannel) {
                messageChannel = (MessageChannel) channel;
            }
        }
        MessageChannel bindedChannel = Util.bindMessageChannel(bot, messageChannel);
        if (bindedChannel == null) {
            return;
        }
        if (file.contains("www") || file.contains("http") || file.contains("https")) {
            try {
                URLConnection connection = new URL(file).openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/4.77");
                String extension = file.substring(file.lastIndexOf("."));
                if (message != null) {
                    bindedChannel.sendMessage(message)
                            .addFile(connection.getInputStream(), "file." + extension).queue();
                } else {
                    bindedChannel.sendFile(connection.getInputStream(), "file." + extension).queue();
                }

            } catch (MalformedURLException e1) {
                Vixio.getErrorHandler().warn("Vixio attempted to set the avatar of a bot but the URL was invalid/was unable to be loaded.");
            } catch (IOException e1) {
                Vixio.getErrorHandler().warn("Vixio attempted to set the avatar of a bot with a URL but was unable to load the URL.");
            } catch (IllegalArgumentException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to upload a file that was larger than 8mb!");
            }
        } else {
            File toSend = new File(file);
            if (toSend.exists()) {
                if (message != null) {
                    bindedChannel.sendMessage(message)
                            .addFile(toSend).queue();
                } else {
                    bindedChannel.sendFile(toSend).queue();
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
        return true;
    }
}

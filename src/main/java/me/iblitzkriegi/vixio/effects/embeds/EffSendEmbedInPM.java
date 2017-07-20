package me.iblitzkriegi.vixio.effects.embeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "SendEmbedToUser",
        title = "Send Embed To User",
        desc = "Send a Embed to a user",
        syntax = "send embed %string% to user %user% with %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$nowplaying\\\":\\n" +
                "\\t\\tset {ptsd} to track player {name} is playing\\n" +
                "\\t\\tmake embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tset title of embed \\\"Nowplaying\\\" to \\\"Displaying information for the track that is currently playing\\\"\\n" +
                "\\t\\tset color of embed \\\"Nowplaying\\\" to \\\"PINK\\\"\\n" +
                "\\t\\tadd field \\\"**Title**\\\", with value \\\"%title of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tsend embed \\\"Nowplaying\\\" to user event-user with \\\"Rawr\\\"\\n" +
                "\\t\\tclear embed \\\"Nowplaying\\\"")
public class EffSendEmbedInPM extends Effect{
    Expression<String> vName;
    Expression<User> vUser;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        MessageEmbed embed = embedBuilder.build();
        MessageBuilder mbuilder = new MessageBuilder();
        mbuilder.setEmbed(embed);
        Message message = mbuilder.build();
        jda.getUserById(vUser.getSingle(e).getId()).openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(message).queue());

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vUser = (Expression<User>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

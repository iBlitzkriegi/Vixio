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
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "SendEmbed",
        title = "Send Embed",
        desc = "Send a Embed to a channel",
        syntax = "send embed %string% to channel %string% with %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$nowplaying\\\":\\n" +
                "\\t\\tset {ptsd} to track player {name} is playing\\n" +
                "\\t\\tclear embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tmake embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tset title of embed \\\"Nowplaying\\\" to \\\"Displaying information for the track that is currently playing\\\"\\n" +
                "\\t\\tset color of embed \\\"Nowplaying\\\" to \\\"PINK\\\"\\n" +
                "\\t\\tadd field \\\"**Title**\\\", with value \\\"%title of track {ptsd}%\\\", split true to embed \\\"Nowplaying\\\"\\n" +
                "\\t\\tsend embed \\\"Nowplaying\\\" to channel event-channel with \\\"Rawr\\\"")
public class EffSendEmbed extends Effect{
    Expression<String> vName;
    Expression<String> vChannel;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        MessageEmbed embed = embedBuilder.build();
        MessageBuilder mbuilder = new MessageBuilder();
        mbuilder.setEmbed(embed);
        Message message = mbuilder.build();
        jda.getTextChannelById(vChannel.getSingle(e)).sendMessage(message).queue();

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vChannel = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

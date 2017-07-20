package me.iblitzkriegi.vixio.effects.embeds;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(
        name = "SetThumbnailOfEmbed",
        title = "Set Thumbnail Of Embed",
        desc = "Set the Thumbnail of one of your Embeds",
        syntax = "set thumbnail of embed %string% to %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tif {_command} starts with \\\"$user\\\":\\n" +
                "\\t\\tbroadcast \\\"%event-message%\\\"\\n" +
                "\\t\\tif message event-message contains mention:\\n" +
                "\\t\\t\\tif size of mentioned users in message event-message is less than 2:\\n" +
                "\\t\\t\\t\\tset {_isbot} to false\\n" +
                "\\t\\t\\t\\tset {num} to 0\\n" +
                "\\t\\t\\t\\tset {_var::*} to event-mentioned\\n" +
                "\\t\\t\\t\\tloop {_var::*}:\\n" +
                "\\t\\t\\t\\t\\tif {num} = 0:\\n" +
                "\\t\\t\\t\\t\\t\\tif user loop-value is bot:\\n" +
                "\\t\\t\\t\\t\\t\\t\\tset {_isbot} to \\\"TRUE\\\"\\n" +
                "\\t\\t\\t\\t\\t\\telse:\\n" +
                "\\t\\t\\t\\t\\t\\t\\tset {_isbot} to \\\"FALSE\\\"\\n" +
                "\\t\\t\\t\\t\\t\\tmake embed \\\"Info\\\"\\n" +
                "\\t\\t\\t\\t\\t\\tset title of embed \\\"Info\\\" to \\\"Who is %name of loop-value%\\\"\\n" +
                "\\t\\t\\t\\t\\t\\tset thumbnail of embed \\\"Info\\\" to \\\"%avatar url of loop-value%\\\"\\n" +
                "\\t\\t\\t\\t\\t\\tset desc of embed \\\"Info\\\" to \\\"Name: **[%name of loop-value%##%discriminator of loop-value%](.)**%nl%ID: **[%id of loop-value%](.)**%nl%Joined Discord: **[%join discord date of user loop-value%](.)**%nl%%nl%**__General__**%nl%Status: **[%status of loop-value in guild event-guild%](.)**%nl%Nick: **[%nickname of user loop-value in guild event-guild%](.)**%nl%Game: **[%current game of user loop-value in guild event-guild%](.)**%nl%Bot: **[%{_isbot}%](.)**%nl%Joined Guild: **[%join guild event-guild date of user loop-value%](.)**\\\"\\n" +
                "\\t\\t\\t\\t\\t\\tsend embed \\\"Info\\\" to channel event-channel with \\\"Rawr\\\"\\n" +
                "\\t\\t\\t\\t\\t\\texit\\n" +
                "\\t\\t\\telse:\\n" +
                "\\t\\t\\t\\treply with \\\"You may only specify one user at a time silly!\\\"\\n" +
                "\\t\\telse:\\n" +
                "\\t\\t\\treply with \\\"You must specify a user to get information on silly!\\\"")
public class EffSetThumbnailOfEmbed extends Effect{
    Expression<String> vName;
    Expression<String> vTitle;
    @Override
    protected void execute(Event e) {
        EmbedBuilder builder = EffCreateEmbed.embedBuilders.get(vName.getSingle(e));
        builder.setThumbnail(vTitle.getSingle(e));
        EffCreateEmbed.embedBuilders.put(vName.getSingle(e), builder);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) expr[0];
        vTitle = (Expression<String>) expr[1];
        return true;
    }
}

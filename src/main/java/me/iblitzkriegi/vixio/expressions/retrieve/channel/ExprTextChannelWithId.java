package me.iblitzkriegi.vixio.expressions.retrieve.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

import java.util.Set;

public class ExprTextChannelWithId extends SimpleExpression<TextChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprTextChannelWithId.class, TextChannel.class, ExpressionType.SIMPLE,
                "text(-| ) channel with id %string% [in %-guild%]")
                .setName("Text Channel with ID")
                .setDesc("Get a Text channel by it's ID, can include the Guild it is in for faster results.")
                .setExample("guild message received:" +
                        "\tif name of event-bot contains \"Jewel\":\t\t" +
                        "\t\tset {_cmd::*} to event-string split at \" \"" +
                        "\t\tif {_cmd::1} is \"##topic\":" +
                        "\t\t\tif {_cmd::2} is not set:" +
                        "\t\t\t\treply with \"You must include an ID to find the channel by!\"" +
                        "\t\t\t\tstop" +
                        "\t\t\tif {_cmd::3} is not set:" +
                        "\t\t\t\treply with \"You must give a topic to set.\"" +
                        "\t\t\t\tstop" +
                        "\t\t\tset topic of text channel with id \"%{_cmd::2}%\" as event-bot to \"%{_cmd::3}%\"");
    }

    private Expression<String> id;
    private Expression<Guild> guild;

    @Override
    protected TextChannel[] get(Event e) {
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }

        if (guild != null) {
            Guild guild = this.guild.getSingle(e);
            TextChannel textChannel = guild.getTextChannelById(id);
            if (textChannel != null) {
                return new TextChannel[]{textChannel};

            }

            return null;
        }

        Set<JDA> jdaInstances = Vixio.getInstance().botHashMap.keySet();
        if (jdaInstances.isEmpty()) {
            Vixio.getErrorHandler().warn("Vixio attempted to get a voice channel by ID but no Bots were logged in to do so.");
            return null;
        }

        for (JDA jda : jdaInstances) {
            TextChannel textChannel = jda.getTextChannelById(id);
            if (textChannel != null) {
                return new TextChannel[]{textChannel};
            }
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends TextChannel> getReturnType() {
        return TextChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "voice channel with id " + id.toString(e, debug) + (guild == null ? "" : " in " + guild.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

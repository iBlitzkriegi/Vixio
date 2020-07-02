package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprChannels extends SimpleExpression<GuildChannel> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannels.class, GuildChannel.class,
                "channel", "guild/category")
                .setName("Channels of")
                .setDesc("Get all of the channels of a guild or category.")
                .setExample(
                        "discord command $channels [<text>]:",
                        "\ttrigger:",
                        "\t\tif arg-1 is not set:",
                        "\t\t\treply with \"Here are the current channels: `%channels of event-guild%`\"",
                        "\t\t\tstop",
                        "\t\tset {_category} to category named arg-1",
                        "\t\treply with \"Here are the channels of the category named %arg-1%: `%channels of {_category}%`\""
                );
    }

    private Expression<Object> object;

    @Override
    protected GuildChannel[] get(Event e) {
        Object object = this.object.getSingle(e);

        if (object instanceof Guild) {
            Guild guild = (Guild) object;
            List<GuildChannel> channels = new ArrayList<>();
            channels.addAll(guild.getTextChannels());
            channels.addAll(guild.getVoiceChannels());

            return channels.toArray(new GuildChannel[channels.size()]);
        } else if (object instanceof Category) {
            List<GuildChannel> channels = ((Category) object).getChannels();
            return channels.toArray(new GuildChannel[channels.size()]);
        }

        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends GuildChannel> getReturnType() {
        return GuildChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "channels of " + object.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }
}

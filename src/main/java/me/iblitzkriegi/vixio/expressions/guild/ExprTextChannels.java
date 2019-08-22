package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

import java.util.List;

public class ExprTextChannels extends SimpleExpression<TextChannel> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprTextChannels.class, TextChannel.class,
                "text(-| )channel", "guild/category")
                .setName("Text Channels of")
                .setDesc("Get all of the text channels in a guild or a category.")
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
    protected TextChannel[] get(Event e) {
        Object object = this.object.getSingle(e);
        if (object instanceof Category) {
            List<TextChannel> channels = ((Category) object).getTextChannels();
            return channels.toArray(new TextChannel[channels.size()]);
        } else if (object instanceof Guild) {
            List<TextChannel> channels = ((Guild) object).getTextChannels();
            return channels.toArray(new TextChannel[channels.size()]);
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends TextChannel> getReturnType() {
        return TextChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "text channels of " + object.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }
}

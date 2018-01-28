package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

import java.util.List;

public class ExprTextchannelsOf extends SimpleExpression<TextChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprTextchannelsOf.class, TextChannel.class, ExpressionType.SIMPLE,
                "text[(-| )]channel[s] of %guild/category%")
                .setName("Text Channels of")
                .setDesc("Get all of the Text Channels in a Guild/Category.")
                .setExample(
                        "on guild message receive:",
                        "\tset {channels::*} to text channels of event-guild",
                        "\tloop {channels::*}:",
                        "\t\tbroadcast \"%name of loop-value%\""
                );
    }

    private Expression<Object> object;

    @Override
    protected TextChannel[] get(Event e) {
        Object object = this.object.getSingle(e);
        if (object == null) {
            return null;
        }
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

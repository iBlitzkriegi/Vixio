package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.scopes.ScopeMakeChannel;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprLastChannelBuilder extends SimpleExpression<ChannelBuilder> {
    static {
        Vixio.getInstance().registerExpression(ExprLastChannelBuilder.class, ChannelBuilder.class, ExpressionType.SIMPLE,
                "[the] last[ly] [(made|created)] channel[[ ]builder]")
                .setName("Last created Channel Builder")
                .setDesc("Get the last made Channel Builder created via the Channel Builder scope.")
                .setExample(
                        "command /lastMade:",
                        "\ttrigger:",
                        "\t\tcreate the last made channel in guild with id \"41561890890\" as \"Jewel\""

                );
    }
    @Override
    protected ChannelBuilder[] get(Event e) {
        ChannelBuilder channelBuilder = ScopeMakeChannel.channelBuilder;
        if (ScopeMakeChannel.channelBuilder == null) {
            return null;
        }
        return new ChannelBuilder[]{channelBuilder};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ChannelBuilder> getReturnType() {
        return ChannelBuilder.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last channel builder";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

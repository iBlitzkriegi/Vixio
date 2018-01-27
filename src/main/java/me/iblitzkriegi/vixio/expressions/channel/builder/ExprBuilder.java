package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.scopes.ScopeMakeChannel;
import me.iblitzkriegi.vixio.util.EffectSection;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprBuilder extends SimpleExpression<ChannelBuilder> {
    static {
        Vixio.getInstance().registerExpression(ExprBuilder.class, ChannelBuilder.class, ExpressionType.SIMPLE,
                "[(the|an|[a] new)] channel[(-| ) builder]")
                .setName("The Channel")
                .setDesc("Get the channel in a create channel scope")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\tset name of the channel to \"Testing\"",
                        "\t\tset {guild} to guild with id \"56156156615611\"",
                        "\t\tcreate the channel in {guild} as \"Jewel\""
                );

    }

    private boolean scope = false;

    @Override
    protected ChannelBuilder[] get(Event e) {
        return new ChannelBuilder[]{
                scope ? ScopeMakeChannel.channelBuilder : new ChannelBuilder()
        };
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
        return "the channel builder";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        scope = EffectSection.isCurrentSection(ScopeMakeChannel.class);
        return true;
    }
}

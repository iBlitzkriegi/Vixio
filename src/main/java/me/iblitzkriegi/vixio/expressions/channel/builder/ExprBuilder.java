package me.iblitzkriegi.vixio.expressions.channel.builder;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.sections.SectionMakeChannel;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;

public class ExprBuilder extends SimpleExpression<ChannelBuilder> {
    static {
        Vixio.getInstance().registerExpression(ExprBuilder.class, ChannelBuilder.class, ExpressionType.SIMPLE,
                "[(the|a)][new] channel[(-| )builder]")
                .setName("The Channel")
                .setDesc("Get the channel in a create channel scope")
                .setExample(
                        "discord command $setup:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\t\tset the name of the channel to \"Bot channel\"",
                        "\t\t\tset the topic of the channel to \"Hi Pika\"",
                        "\t\t\tcreate the channel in event-guild and store it in {_chnl}"
                );

    }

    private boolean scope = false;

    @Override
    protected ChannelBuilder[] get(Event e) {
        return new ChannelBuilder[]{
				scope ? SectionMakeChannel.channelBuilder : new ChannelBuilder()
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
		scope = EffectSection.isCurrentSection(SectionMakeChannel.class);
        return true;
    }
}

package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprBuilderBitrate extends SimplePropertyExpression<ChannelBuilder, Integer> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderBitrate.class, Integer.class,
                "bitrate", "channelbuilders")
                .setName("Bitrate of voice channel")
                .setDesc("Get the bitrate of a channel builder. " +
                        "The default value is 64kbps for channel builders. Rates multiplied by 1000." +
                        "You can set or reset this (resets to 64kbps)")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate voice channel:",
                        "\t\t\tset name of the channel to \"Testing\"",
                        "\t\t\tset bitrate of the channel as \"Jewel\" to 69",
                        "\t\tcreate the channel in guild with id \"56156156615611\" as \"Jewel\""
                );
    }

    @Override
    public Integer convert(ChannelBuilder builder) {
        return builder.getBitRate() / 1000;
    }

    @Override
    protected String getPropertyName() {
        return "bitrate";
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Number.class};
        }
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<ChannelBuilder>) exprs[0]);
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (ChannelBuilder builder : getExpr().getAll(e)) {
            builder.setBitRate(mode == Changer.ChangeMode.SET ? ((Number) delta[0]).intValue() * 1000 : Util.DEFAULT_BITRATE);
        }
    }
}

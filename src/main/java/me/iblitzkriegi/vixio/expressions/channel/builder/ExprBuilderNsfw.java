package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprBuilderNsfw extends SimplePropertyExpression<ChannelBuilder, Boolean> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderNsfw.class, Boolean.class,
                "nsfw state", "channelbuilders")
                .setName("NSFW State")
                .setDesc("Returns whether or not a channel builder is NSFW (false by default).")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\tset name of the channel to \"Testing\"",
                        "\t\tset {guild} to guild with id \"56156156615611\"",
                        "\t\tset nsfw state of this channel as \"Jewel\" to true",
                        "\t\tcreate the channel in {guild} as \"Jewel\""
                );
    }

    @Override
    public Boolean convert(ChannelBuilder builder) {
        return builder.isNSFW();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    protected String getPropertyName() {
        return "nsfw state";
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Boolean.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (ChannelBuilder builder : getExpr().getAll(e)) {
            builder.setNSFW(mode == Changer.ChangeMode.RESET ? false : (Boolean) delta[0]);
        }
    }
}

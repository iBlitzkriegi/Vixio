package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprBuilderLimit extends SimplePropertyExpression<ChannelBuilder, Integer> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderLimit.class, Integer.class,
                "user limit", "channelbuilders")
                .setName("User limit of a channel builder")
                .setDesc("Get or sets the user limit of a channel builder")
                .setExample(
                        "discord command $create:",
                        "\ttrigger:",
                        "\t\tcreate voice channel:",
                        "\t\t\tset the name of the channel to \"{@bot}\"",
                        "\t\t\tset the user limit of the channel to 5",
                        "\t\tcreate the last made channel in event-guild and store it in {_chnl}",
                        "\t\treply with \"I have successfully created the channel! ID: %id of {_chnl}%\""
                );
    }

    @Override
    public Integer convert(ChannelBuilder builder) {
        return builder.getUserLimit();
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    protected String getPropertyName() {
        return "user limit";
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Number.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (ChannelBuilder builder : getExpr().getAll(e)) {
            builder.setUserLimit(mode == Changer.ChangeMode.DELETE ? 0 : (((Number) delta[0]).intValue()));
        }
    }

}

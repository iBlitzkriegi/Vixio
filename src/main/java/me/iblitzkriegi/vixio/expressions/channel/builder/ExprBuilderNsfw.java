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
                .setName("NSFW State of a channel builder")
                .setDesc("Returns whether or not a channel builder is NSFW (false by default). Can be set to either true or false.")
                .setExample(
                        "discord command $create <text> <boolean>:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\t\tset the name of the channel to arg-1",
                        "\t\t\tset the nsfw state of the channel to arg-2",
                        "\t\tcreate the last made channel in event-guild and store it in {_chnl}",
                        "\t\treply with \"I've successfully created a channel named `%arg-1%`, ID: %id of {_chnl}%\""
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

package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprBuilderName extends SimplePropertyExpression<ChannelBuilder, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderName.class, String.class, "name", "channelbuilders")
                .setName("Name of channel builder")
                .setDesc("Get the name of a channel builder. Can be set to a string.")
                .setExample(
                        "discord command $create <text>:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\t\tset the name of the channel to arg-1",
                        "\t\tcreate the last made channel in event-guild and store it in {_chnl}",
                        "\t\treply with \"I have successfully created the channel! ID: %id of {_chnl}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "name";
    }

    @Override
    public String convert(ChannelBuilder channelBuilder) {
        return channelBuilder.getName();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (ChannelBuilder builder : getExpr().getAll(e)) {
            builder.setName((String) delta[0]);
        }
    }
}

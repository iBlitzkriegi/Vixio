package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.api.entities.Category;
import org.bukkit.event.Event;

public class ExprBuilderParent extends SimplePropertyExpression<ChannelBuilder, Category> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderParent.class, Category.class,
                "(category|parent)", "channelbuilders")
                .setName("Category of channel builder")
                .setDesc("Get or set the category of a channel builder.")
                .setExample(
                        "discord command $create <text>:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\t\tset the name of the channel to arg-1 ",
                        "\t\t\tset the parent of the channel to category named \"xd\"",
                        "\t\tcreate the last made channel in event-guild and store it in {_chnl}",
                        "\t\treply with \"I've successfully created a channel named `%arg-1%`, ID: %id of {_chnl}%\""
                );
    }

    @Override
    public Category convert(ChannelBuilder builder) {
        return builder.getParent();
    }

    @Override
    public Class<? extends Category> getReturnType() {
        return Category.class;
    }

    @Override
    protected String getPropertyName() {
        return "category";
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Category.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (ChannelBuilder builder : getExpr().getAll(e)) {
            builder.setParent(mode == Changer.ChangeMode.DELETE ? null : (Category) delta[0]);
        }
    }

}

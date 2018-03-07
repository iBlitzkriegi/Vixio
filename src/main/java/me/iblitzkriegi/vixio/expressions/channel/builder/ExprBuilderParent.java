package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.Category;
import org.bukkit.event.Event;

public class ExprBuilderParent extends SimplePropertyExpression<ChannelBuilder, Category> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderParent.class, Category.class,
                "(category|parent)", "channelbuilders")
                .setName("Category of channel")
                .setDesc("Get or set the category of a channel.")
                .setExample("discord command channel <text>",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\treply with \"%category of a new channel builder%\""
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

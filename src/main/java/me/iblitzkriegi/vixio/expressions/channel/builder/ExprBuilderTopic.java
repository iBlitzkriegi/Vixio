package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import org.bukkit.event.Event;

public class ExprBuilderTopic extends SimplePropertyExpression<ChannelBuilder, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprBuilderTopic.class, String.class,
                "topic", "channelbuilders")
                .setName("Topic of Channel")
                .setDesc("Gets or sets the topic of a channel builder.")
                .setExample("set topic of event-channel to \"Hi Pika\"");
    }

    @Override
    public String convert(ChannelBuilder builder) {
        return builder.getTopic();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "topic";
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE)
            return new Class[]{String.class};
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (ChannelBuilder builder : getExpr().getAll(e)) {
            builder.setTopic(mode == Changer.ChangeMode.DELETE ? null : (String) delta[0]);
        }
    }

}

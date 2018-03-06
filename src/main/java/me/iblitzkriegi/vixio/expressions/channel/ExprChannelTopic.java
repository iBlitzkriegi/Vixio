package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprChannelTopic extends ChangeableSimplePropertyExpression<TextChannel, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelTopic.class, String.class,
                "topic", "textchannels")
                .setName("Topic of Channel")
                .setDesc("Get or set the topic of a text channel.")
                .setExample("set topic of event-textchannel to \"Hi Pika\" with event-bot");
    }

    @Override
    public String convert(TextChannel channel) {
        return channel.getTopic();
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
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE)
            return new Class[]{String.class};
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (TextChannel channel : getExpr().getAll(e)) {
            channel = Util.bindChannel(bot, channel);
            if (channel != null) {
                try {
                    channel.getManager().setTopic(mode == Changer.ChangeMode.DELETE ? null : (String) delta[0]).queue();
                } catch (PermissionException ex) {
                    Vixio.getErrorHandler().needsPerm(bot, mode.name().toLowerCase() + " topic", ex.getPermission().getName());
                }
            }
        }
    }

}

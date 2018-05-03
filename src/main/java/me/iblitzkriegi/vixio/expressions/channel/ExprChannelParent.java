package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprChannelParent extends ChangeableSimplePropertyExpression<Channel, Category> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelParent.class, Category.class,
                "(category|parent)", "channels")
                .setName("Category of channel")
                .setDesc("Get or set the category of a channel.")
                .setExample("discord command channel <text>",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\treply with \"%(category of channel with id arg-1)'s name%\""
                );
    }

    @Override
    public Category convert(Channel channel) {
        return channel.getParent();
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
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{Category.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Channel channel : getExpr().getAll(e)) {
            channel = Util.bindChannel(bot, channel);
            if (channel != null) {
                try {
                    channel.getManager().setParent((Category) delta[0]).queue();
                } catch (PermissionException ex) {
                    Vixio.getErrorHandler().needsPerm(bot, "set category", ex.getPermission().getName());
                }
            }
        }
    }

    @Override
    public boolean shouldError() {
        return false;
    }

}

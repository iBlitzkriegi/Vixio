package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

public class ExprChannelNsfw extends ChangeableSimplePropertyExpression<TextChannel, Boolean> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelNsfw.class, Boolean.class,
                "nsfw state", "textchannels")
                .setName("NSFW State")
                .setDesc("Returns whether or not a channel is NSFW (false by default).")
                .setExample(
                        "discord command nsfw <boolean>:",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tset nsfw state of event-channel to arg-1"
                );
    }

    @Override
    public Boolean convert(TextChannel channel) {
        return channel.isNSFW();
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
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Boolean.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (TextChannel channel : getExpr().getAll(e)) {
            channel = Util.bindChannel(bot, channel);
            if (channel != null) {
                channel.getManager().setNSFW(mode == Changer.ChangeMode.RESET ? false : (Boolean) delta[0]).queue();
            }
        }
    }

    @Override
    public boolean shouldError() {
        return false;
    }
}

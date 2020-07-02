package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprSlowMode extends ChangeableSimplePropertyExpression<GuildChannel, Number> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprSlowMode.class, Number.class, "slowmode", "channel/textchannel")
                .setName("Slowmode of TextChannel")
                .setDesc("Set the slowmode of a text channel. Only text channels can have have their slowmode set and retrieved. This can be set, reset, and deleted.")
                .setExample("broadcast \"slowmode of event-channel\"");
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Number.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        GuildChannel[] guildChannels = getExpr().getAll(e);
        if (guildChannels == null || guildChannels.length == 0) {
            return;
        }

        switch (mode) {
            case RESET:
            case DELETE:
                for (GuildChannel guildChannel : guildChannels) {
                    if (!(guildChannel instanceof TextChannel)) {
                        continue;
                    }
                    TextChannel boundChannel = Util.bindChannel(bot, (TextChannel) guildChannel);
                    if (boundChannel == null) {
                        continue;
                    }
                    try {
                        boundChannel.getManager().setSlowmode(0).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "Remove slowmode for channel", x.getPermission().getName());
                    }
                }
                break;
            case SET:
                Number new_mode = (Number) delta[0];
                for (GuildChannel guildChannel : guildChannels) {
                    if (!(guildChannel instanceof TextChannel)) {
                        continue;
                    }
                    GuildChannel boundChannel = Util.bindChannel(bot, (TextChannel) guildChannel);
                    if (boundChannel == null) {
                        continue;
                    }
                    try {
                        boundChannel.getManager().setSlowmode(new_mode.intValue()).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "Set slowmode of channel", x.getPermission().getName());
                    }
                }
            default:
                break;
        }
    }

    @Override
    protected String getPropertyName() {
        return "slowmode";
    }

    @Override
    public Number convert(GuildChannel guildChannel) {
        if (!(guildChannel instanceof TextChannel)) {
            return null;
        }
        return ((TextChannel) guildChannel).getSlowmode();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}

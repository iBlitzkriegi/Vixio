package me.iblitzkriegi.vixio.effects.channel;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffSendTyping extends Effect{

    static {
        Vixio.getInstance().registerEffect(EffSendTyping.class, "send typing in %channel% [with %bot/string%]")
                .setName("Send typing")
                .setDesc("Make a bot start typing in a text channel.")
                .setUserFacing("send typing in %textchannel% [with %bot/string%]")
                .setExample("send typing in event-channel");
    }

    private Expression<TextChannel> channel;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        TextChannel channel = this.channel.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null || channel == null) {
            return;
        }

        TextChannel bindedChannel = Util.bindChannel(bot, channel);
        if (bindedChannel != null) {
            try {
                bindedChannel.sendTyping().queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "send typing", x.getPermission().getName());
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send typing in " + channel.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channel = (Expression<TextChannel>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}

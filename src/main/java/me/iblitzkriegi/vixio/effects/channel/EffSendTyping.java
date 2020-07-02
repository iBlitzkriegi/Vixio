package me.iblitzkriegi.vixio.effects.channel;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffSendTyping extends Effect{

    static {
        Vixio.getInstance().registerEffect(EffSendTyping.class, "send typing (to|in) %channel/user% [with %bot/string%]")
                .setName("Send typing")
                .setDesc("Make a bot start typing in a text channel.")
                .setUserFacing("send typing in %textchannel/user% [with %bot/string%]")
                .setExample("send typing in event-channel");
    }

    private Expression<Object> location;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Object location = this.location.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null || location == null) {
            return;
        }

        if (location instanceof User) {
            User user = Util.bindUser(bot, (User) location);
            if (user != null) {
                user.openPrivateChannel().queue(privateChannel -> privateChannel.sendTyping().queue());
            }
        } else if (location instanceof TextChannel) {
            TextChannel boundChannel = Util.bindChannel(bot, (TextChannel) location);
            if (boundChannel != null) {
                try {
                    boundChannel.sendTyping().queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "send typing", x.getPermission().getName());
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send typing in " + location.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        location = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}

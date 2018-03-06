package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

public class EffNsfw extends Effect {

    static {

        Vixio.getInstance().registerEffect(EffNsfw.class,
                "(make|mark) %textchannels% [as] [<n>]sfw (with|using) %bot/string%");

    }

    private boolean newState;
    private Expression<Bot> bot;
    private Expression<TextChannel> channels;

    @Override
    protected void execute(Event e) {
        Bot bot = this.bot.getSingle(e);
        if (bot == null) {
            return;
        }
        for (TextChannel channel : channels.getAll(e)) {
            channel = Util.bindChannel(bot, channel);
            if (channel != null) {
                channel.getManager().setNSFW(newState).queue();
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make " + channels.toString(e, debug) + (newState ? "nsfw" : "sfw") + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channels = (Expression<TextChannel>) exprs[0];
        bot = (Expression<Bot>) exprs[1];
        newState = parseResult.regexes.size() == 1;
        return true;
    }

}

package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

public class EffNsfw extends Effect {

    static {
        Vixio.getInstance().registerEffect(EffNsfw.class,
                "(make|mark) %channels% [as] [<n>]sfw (with|using) %bot/string%")
                .setName("Change NSFW")
                .setDesc("Lets you mark a text channel as sfw/nsfw")
                .setUserFacing("(make|mark) %textchannels% [as] [<n>]sfw (with|using) %bot/string%")
                .setExample(
                        "discord command $nsfw <boolean>:",
                        "\ttrigger:",
                        "\t\tif arg-1 is true:",
                        "\t\t\tmark event-channel as nsfw with event-bot",
                        "\t\t\tstop",
                        "\t\tmark event-channel as sfw with event-bot"
                );
    }

    private boolean newState;
    private Expression<Bot> bot;
    private Expression<GuildChannel> channels;

    @Override
    protected void execute(Event e) {
        Bot bot = this.bot.getSingle(e);
        if (bot == null) {
            return;
        }

        for (GuildChannel channel : channels.getAll(e)) {
            if (channel instanceof TextChannel) {
                channel = Util.bindChannel(bot, (TextChannel) channel);
                if (channel != null) {
                    channel.getManager().setNSFW(newState).queue();
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make " + channels.toString(e, debug) + (newState ? "nsfw" : "sfw") + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channels = (Expression<GuildChannel>) exprs[0];
        bot = (Expression<Bot>) exprs[1];
        newState = parseResult.regexes.size() == 1;
        return true;
    }

}

package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Event;

public class CondNsfw extends Condition implements EasyMultiple<Object, Void> {

    static {
        Vixio.getInstance().registerCondition(CondNsfw.class,
                "%channelbuilders/channels% (is|are) nsfw", "%channelbuilders/channels% (is|are) sfw")
                .setName("NSFW")
                .setDesc("Lets you check if a channel builder or text channel is nsfw/sfw.")
                .setUserFacing("[the] nsfw state[s] of %channelbuilders/textchannels%", "%channelbuilders/textchannels%'[s] nsfw state[s]")
                .setExample(
                        "discord command nsfw:",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tif event-channel is nsfw:",
                        "\t\t\treply with \"%event-channel% is nsfw\""
                );
    }

    private Expression<Object> channels;

    @Override
    public boolean check(Event e) {
        return check(channels.getAll(e), o -> {
            if (o instanceof ChannelBuilder) {
                return ((ChannelBuilder) o).isNSFW();
            } else {
                return o instanceof TextChannel ? ((TextChannel)o).isNSFW() : false;
            }
        }, isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return channels.toString() + " is " + (isNegated() ? "" : "n") + "sfw";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channels = (Expression<Object>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }

}

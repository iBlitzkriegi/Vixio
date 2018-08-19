package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class CondNsfw extends PropertyCondition<Object> {

    static {
        Vixio.getInstance().registerPropertyCondition(CondNsfw.class,
                "(nsfw|1¦sfw)", "channels/channelbuilders")
                .setName("Is NSFW")
                .setDesc("Lets you check if a channel builder or text channel is nsfw/sfw.")
                .setUserFacing("%channelbuilders/textchannels% (is|are) (nsfw|sfw)",
                            "%channelbuilders/textchannels% (isn't|is not|aren't|are not) (nsfw|sfw)")
                .setExample(
                        "discord command nsfw:",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tif event-channel is nsfw:",
                        "\t\t\treply with \"%event-channel% is nsfw\""
                );
    }

    @Override
    public boolean check(Object channel) {
        if (!(channel instanceof TextChannel || channel instanceof ChannelBuilder))
            return false;
        return channel instanceof TextChannel ? ((TextChannel) channel).isNSFW() : ((ChannelBuilder) channel).isNSFW();
    }

    @Override
    protected String getPropertyName() {
        return "nsfw";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setNegated(matchedPattern == 1 || parseResult.mark == 1);
        return true;
    }

}

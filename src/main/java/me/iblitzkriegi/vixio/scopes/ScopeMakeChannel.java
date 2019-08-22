package me.iblitzkriegi.vixio.scopes;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import org.bukkit.event.Event;

public class ScopeMakeChannel extends EffectSection {

    public static ChannelBuilder channelBuilder;

    static {
        Vixio.getInstance().registerCondition(ScopeMakeChannel.class, "(make|create) [text] channel", "(make|create) voice channel")
                .setName("Create channel scope")
                .setDesc("Provides a easy way to create either a text channel or a voice channel.")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\tset name of the channel to \"Testing\"",
                        "\t\tset {guild} to guild with id \"56156156615611\"",
                        "\t\tset nsfw state of the channel to true",
                        "\t\tcreate the channel in {guild} with \"Jewel\""
                );
    }

    private boolean channelType;

    @Override
    public void execute(Event e) {
        channelBuilder = new ChannelBuilder();
        if (channelType) {
            channelBuilder.setType(ChannelType.TEXT);
        } else {
            channelBuilder.setType(ChannelType.VOICE);
        }
        runSection(e);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create channel";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Vixio.getErrorHandler().warn("Vixio attempted to create a channel but no section was found.");
            return false;
        }
        channelType = i == 0;
        loadSection(true);
        return true;
    }
}

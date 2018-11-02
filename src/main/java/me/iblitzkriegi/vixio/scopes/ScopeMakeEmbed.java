package me.iblitzkriegi.vixio.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

public class ScopeMakeEmbed extends EffectSection {

    public static EmbedBuilder lastEmbed;

    static {
        Vixio.getInstance().registerCondition(ScopeMakeEmbed.class, "(make|create) (embed|embed %-embedbuilder%)")
                .setName("Make Embed")
                .setDesc("Provides a pretty and easy way of making a new embed with a bunch of different attributes")
                .setExample(
                        "command $scope:",
                        "\ttrigger:",
                        "\t\tmake a new embed:",
                        "\t\t\tset color of the embed to red",
                        "\t\t\tset url of the embed to \"https://google.com\"",
                        "\t\t\tset title of the embed to \"Google!\"",
                        "\t\tset {_embed} to last made embed"
                );
    }

    private Expression<EmbedBuilder> builder;

    @Override
    public void execute(Event e) {
        EmbedBuilder embed = builder == null ? new EmbedBuilder() : builder.getSingle(e);
        lastEmbed = embed == null ? new EmbedBuilder() : embed;
        runSection(e);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make embed " + builder.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Skript.error("An embed creation scope is useless without any content!");
            return false;
        }
        builder = (Expression<EmbedBuilder>) exprs[0];
        loadSection(true);
        return true;
    }
}

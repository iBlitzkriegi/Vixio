package me.iblitzkriegi.vixio.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.EffectSection;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

public class ScopeMakeEmbed extends EffectSection {

    public static EmbedBuilder lastEmbed;

    static {
        Vixio.getInstance().registerCondition(ScopeMakeEmbed.class, "make [embed] %embedbuilder%");
    }

    private Expression<EmbedBuilder> builder;

    @Override
    public void execute(Event e) {
        EmbedBuilder embed = builder.getSingle(e);
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
        if (!hasSection()) { //Self explanatory
            Skript.error("You can't make an embed without a section!");
            return false;
        }

        builder = (Expression<EmbedBuilder>) exprs[0];

        return true;
    }
}

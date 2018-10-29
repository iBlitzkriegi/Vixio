package me.iblitzkriegi.vixio.expressions.embeds;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.sections.SectionMakeEmbed;
import net.dv8tion.jda.core.EmbedBuilder;

public class ExprLastEmbed extends SimpleExpression<EmbedBuilder> {

    static {
        Vixio.getInstance().registerExpression(ExprLastEmbed.class, EmbedBuilder.class, ExpressionType.SIMPLE,
                "[the] last[ly] [(made|created)] embed[[ ]builder]")
                .setName("Last Made Embed")
                .setDesc("Returns the embed that was last made in a embed scope")
                .setExample("set {_embed} to the last embed");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected EmbedBuilder[] get(Event e) {
		return new EmbedBuilder[]{SectionMakeEmbed.lastEmbed};
    }

    @Override
    public Class<? extends EmbedBuilder> getReturnType() {
        return EmbedBuilder.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last made embed";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}

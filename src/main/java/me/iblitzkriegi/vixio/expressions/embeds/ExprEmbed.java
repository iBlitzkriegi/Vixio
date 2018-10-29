package me.iblitzkriegi.vixio.expressions.embeds;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.sections.SectionMakeEmbed;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import net.dv8tion.jda.core.EmbedBuilder;

public class ExprEmbed extends SimpleExpression<EmbedBuilder> {

    static {
        Vixio.getInstance().registerExpression(ExprEmbed.class, EmbedBuilder.class, ExpressionType.SIMPLE,
                "[(the|an|[a] new)] embed")
                .setName("New/Current Embed")
                .setDesc("If it isn't inside an embed scope, this expression returns a new embed. " +
                        "If it is inside of an embed scope, it returns the embed that belongs to that scope.")
                .setExample(
                        "# outside a scope",
                        "set {_e} to a new embed",
                        "make a new embed:",
                        "\tset color of embed the embed to red",
                        "\tset url of the embed to \"https://google.com\"",
                        "\tset title of the embed to \"Google!\"",
                        "set {_embed} to last made embed"
                );
    }

    private boolean scope = false;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
		scope = EffectSection.isCurrentSection(SectionMakeEmbed.class);
        return true;
    }

    @Override
    protected EmbedBuilder[] get(Event e) {
        return new EmbedBuilder[]{
				scope ? SectionMakeEmbed.lastEmbed : new EmbedBuilder()
        };
    }

    @Override
    public Class<? extends EmbedBuilder> getReturnType() {
        return EmbedBuilder.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the embed";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}

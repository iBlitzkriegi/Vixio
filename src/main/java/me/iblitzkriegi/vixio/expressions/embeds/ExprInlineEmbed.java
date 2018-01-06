package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

public class ExprInlineEmbed extends SimpleExpression<EmbedBuilder> {

    static {
        Vixio.getInstance().registerExpression(ExprInlineEmbed.class, EmbedBuilder.class, ExpressionType.COMBINED,
                "%embedbuilder% (with|and) [the] title %string%", "%embedbuilder% (with|and) [the] description %string%")
                .setName("Inline Embed")
                .setDesc("Lets you easily make an embed with a couple common properties.")
                .setExample("set {_embed} to a new embed with the title \"Title\" and the description \"Description\"");
    }

    private boolean title = false;
    private Expression<EmbedBuilder> builder;
    private Expression<String> string;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        title = matchedPattern == 0;
        builder = (Expression<EmbedBuilder>) exprs[0];
        string = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected EmbedBuilder[] get(Event e) {
        EmbedBuilder embed = builder.getSingle(e);
        String str = string.getSingle(e);
        if (embed == null) return null;

        if (title)
            embed.setTitle(str);
        else
            embed.setDescription(str);

        return new EmbedBuilder[]{
                embed
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EmbedBuilder> getReturnType() {
        return EmbedBuilder.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return builder.toString(e, debug) + " with " + (title ? "title " : "description ") + string.toString(e, debug);
    }


}

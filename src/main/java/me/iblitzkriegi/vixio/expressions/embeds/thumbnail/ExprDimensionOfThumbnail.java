package me.iblitzkriegi.vixio.expressions.embeds.thumbnail;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.MessageEmbed.Thumbnail;
import org.bukkit.event.Event;

public class ExprDimensionOfThumbnail extends SimplePropertyExpression<Thumbnail, Number> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprDimensionOfThumbnail.class, Thumbnail.class,
                "[thumbnail] <width|height>[s]", "thumbnails")
                .setName("Dimension of Embed")
                .setDesc("Returns a dimension of an embed. You can specify either width of height.");
    }

    private boolean height = false;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        height = parseResult.regexes.get(0).equals("height");
        setExpr((Expression<Thumbnail>) exprs[0]);
        return true;
    }

    @Override
    public Number convert(final Thumbnail thumb) {
        return height ? thumb.getHeight() : thumb.getWidth();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "dimension of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the " + (height ? "height" : "width") + " of " + getExpr().toString(e, debug);
    }

}

package me.iblitzkriegi.vixio.expressions.embeds.image;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Thumbnail;
import org.bukkit.event.Event;

public class ExprDimensionOfImage extends SimplePropertyExpression<Object, Number> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprDimensionOfImage.class, Number.class,
                "<width|height>[s]", "thumbnails/imageinfos")
                .setName("Dimension of Image")
                .setDesc("Returns a dimension of an embed's thumbnail, image, footer icon or icon. You can specify either width or height.")
                .setExample("reply with \"%width of {_embed}'s thumbnail%\"");
    }

    private boolean height = false;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr(exprs[0]);
        height = parseResult.regexes.get(0).group(0).equals("height");
        return true;
    }

    @Override
    public Number convert(final Object image) {
        if (image instanceof MessageEmbed.ImageInfo) {
            MessageEmbed.ImageInfo img = (MessageEmbed.ImageInfo) image;
            return height ? img.getHeight() : img.getWidth();
        } else if (image instanceof Thumbnail) {
            Thumbnail img = (Thumbnail) image;
            return height ? img.getHeight() : img.getWidth();
        }
        return null;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "dimension of image";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the " + (height ? "height" : "width") + " of " + getExpr().toString(e, debug);
    }

}

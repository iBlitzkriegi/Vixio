package me.iblitzkriegi.vixio.expressions.embeds.image;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Thumbnail;
import org.bukkit.event.Event;

public class ExprImageUrl extends SimplePropertyExpression<Object, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprImageUrl.class, String.class,
                "[<proxy>] url", "thumbnails/imageinfos/footers/authorinfos")
                .setName("Url of Image")
                .setDesc("Returns the url of an embed's thumbnail, footer icon, author, icon or large image. Adding the proxy modifier to the syntax will return Discord's proxy url if possible.")
                .setExample("broadcast \"%proxy url of {_embed}'s thumbnail%\"");
    }

    private boolean proxy = false;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        proxy = parseResult.regexes.size() == 1;
        setExpr(exprs[0]);
        return true;
    }

    @Override
    public String convert(final Object image) {
        if (image instanceof MessageEmbed.ImageInfo) {
            MessageEmbed.ImageInfo img = (MessageEmbed.ImageInfo) image;
            return proxy ? img.getProxyUrl() : img.getUrl();
        } else if (image instanceof Thumbnail) {
            Thumbnail img = (Thumbnail) image;
            return proxy ? img.getProxyUrl() : img.getUrl();
        } else if (image instanceof MessageEmbed.Footer) {
            MessageEmbed.Footer img = (MessageEmbed.Footer) image;
            return proxy ? img.getProxyIconUrl() : img.getIconUrl();
        } else if (image instanceof MessageEmbed.AuthorInfo) {
            MessageEmbed.AuthorInfo img = (MessageEmbed.AuthorInfo) image;
            return proxy ? img.getProxyIconUrl() : img.getIconUrl();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return (proxy ? "proxy " : "") + "image of image";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the " + (proxy ? "proxy " : "") + "url of " + getExpr().toString(e, debug);
    }

}

package me.iblitzkriegi.vixio.expressions.embeds.thumbnail;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.MessageEmbed.Thumbnail;
import org.bukkit.event.Event;

public class ExprUrlOfThumbnail extends SimplePropertyExpression<Thumbnail, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprUrlOfThumbnail.class, String.class,
                "[<proxy>] url[s]", "thumbnails")
                .setName("Url of Thumbnail")
                .setDesc("Returns the url of thumbnail of an embed. Adding the proxy modifier to the syntax will return Discord's proxy url if possible.");
    }

    private boolean proxy = false;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        proxy = parseResult.regexes.size() == 1;
        setExpr((Expression<Thumbnail>) exprs[0]);
        return true;
    }

    @Override
    public String convert(final Thumbnail thumb) {
        return proxy ? thumb.getProxyUrl() : thumb.getUrl();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return (proxy ? "proxy " : "") + "url of thumbnail";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the " + (proxy ? "proxy " : "") + "url of " + getExpr().toString(e, debug);
    }

}

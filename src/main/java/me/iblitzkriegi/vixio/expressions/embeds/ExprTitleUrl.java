package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.embed.Title;
import org.bukkit.event.Event;

public class ExprTitleUrl extends SimplePropertyExpression<Title, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprTitleUrl.class, String.class,
                "(url|link)", "titles")
                .setName("Url of Title")
                .setDesc("Returns the url of a title.")
                .setExample("broadcast \"%link of {_embed}'s title%\"");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<Title>) exprs[0]);
        return true;
    }

    @Override
    public String convert(Title title) {
        return title.getUrl();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "link of title";
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "link of " + getExpr().toString(e, debug);
    }

}

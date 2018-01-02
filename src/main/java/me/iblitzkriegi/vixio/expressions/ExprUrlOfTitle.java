package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Title;
import org.bukkit.event.Event;

public class ExprUrlOfTitle extends SimplePropertyExpression<Title, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprUrlOfTitle.class, String.class,
                "(url|link)[s]", "titles")
                .setName("Url of Title")
                .setDesc("Returns the url of a title.")
                .setExample("broadcast \"%link of {_embed}'s title%\"");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<Title>) exprs[0]);
        return true;
    }

    @Override
    public String convert(final Title title) {
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
    public String toString(final Event e, final boolean debug) {
        return "link of " + getExpr().toString(e, debug);
    }

}

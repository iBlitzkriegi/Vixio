package me.iblitzkriegi.vixio.expressions.embeds.properties;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.embed.Title;
import org.bukkit.event.Event;

public class ExprNewEmbedTitle extends SimpleExpression<Title> {
    static {
        Vixio.getInstance().registerExpression(ExprNewEmbedTitle.class, Title.class, ExpressionType.COMBINED,
                "[a] title [with] [the] text %string%[( and [the]|, )((url|link) %-string%|no (url|link))]")
                .setName("New Title")
                .setDesc("Returns a title with the specified data")
                .setExample("set title of {_embed} to a title with text \"Vixio\" and url \"https://i.imgur.com/TQgR2hW.jpg\"");
    }

    private Expression<String> text;
    private Expression<String> url = null;

    @Override
    protected Title[] get(Event e) {
        String text = this.text.getSingle(e);
        if (text == null) return null;

        return new Title[]{
                new Title(text, (url == null ? null : url.getSingle(e)))
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Title> getReturnType() {
        return Title.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "title with text" + text.toString(e, debug) + (url == null ? "and no url " : " and url " + url.toString(e, debug));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        text = (Expression<String>) exprs[0];
        url = (Expression<String>) exprs[1];
        return true;
    }
}

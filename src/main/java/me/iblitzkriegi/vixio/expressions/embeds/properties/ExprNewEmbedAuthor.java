package me.iblitzkriegi.vixio.expressions.embeds.properties;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprNewEmbedAuthor extends SimpleExpression<MessageEmbed.AuthorInfo> {
    static {
        Vixio.getInstance().registerExpression(ExprNewEmbedAuthor.class, MessageEmbed.AuthorInfo.class, ExpressionType.COMBINED,
                "[an] author named %string% with [([the] url %-string%|no url)[( and [the]|, )]] [(icon %-string%|no icon)]]")
                .setName("New Author Info")
                .setDesc("Returns a author with the specified data")
                .setExample("set author of {_embed} to an author named \"Pikachu\" with the url \"http://vixio.space/docs\" and icon \"https://i.imgur.com/TQgR2hW.jpg\"");
    }

    private Expression<String> text;
    private Expression<String> icon = null;
    private Expression<String> url = null;

    @Override
    protected MessageEmbed.AuthorInfo[] get(Event e) {
        EmbedBuilder builder;
        try {
            builder = new EmbedBuilder().setAuthor(text.getSingle(e), (url == null ? null : url.getSingle(e)), (icon == null ? null : icon.getSingle(e)));
        } catch (IllegalArgumentException e1) {
            Skript.error("Vixio encountered the error \"" + e1.getMessage() + "\" while trying to make " + this.toString(e, false) + "with url \"" + url.getSingle(e) + "\" and icon " + icon.getSingle(e) + "\"");
            return null;
        }
        return new MessageEmbed.AuthorInfo[]{
                builder.isEmpty() ? null : builder.build().getAuthor()
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MessageEmbed.AuthorInfo> getReturnType() {
        return MessageEmbed.AuthorInfo.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "author named " + text.toString(e, debug) + (url == null ? " with no url" : " with the url " + url.toString(e, debug)) + (icon == null ? " and no icon" : " and the icon " + icon.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        text = (Expression<String>) exprs[0];
        url = (Expression<String>) exprs[1];
        icon = (Expression<String>) exprs[2];
        return true;
    }
}

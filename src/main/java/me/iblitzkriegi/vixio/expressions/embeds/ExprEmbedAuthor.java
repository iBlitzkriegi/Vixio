package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprEmbedAuthor extends SimplePropertyExpression<EmbedBuilder, MessageEmbed.AuthorInfo> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedAuthor.class, MessageEmbed.AuthorInfo.class,
                "(author info|author)[s]", "embedbuilders")
                .setName("Author of Embed")
                .setDesc("Returns the author of an embed. Can be set to any author.")
                .setExample("set author of {_embed} to author named \"Pikachu\"");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public MessageEmbed.AuthorInfo convert(EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getAuthor();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
            return new Class[]{
                    MessageEmbed.AuthorInfo.class,
                    String.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        EmbedBuilder[] embeds = getExpr().getAll(e);
        if (embeds == null) return;

        switch (mode) {
            case SET:
                for (EmbedBuilder embedBuilder : embeds) {
                    MessageEmbed.AuthorInfo author = delta[0] instanceof String ?
                            new EmbedBuilder().setAuthor((String) delta[0]).build().getAuthor() :
                            (MessageEmbed.AuthorInfo) delta[0];
                    embedBuilder.setAuthor(author.getName(), author.getUrl(), author.getIconUrl());
                }
                break;
            case RESET:
            case DELETE:
                for (EmbedBuilder embedBuilder : embeds) {
                    embedBuilder.setAuthor(null, null, null);
                }
                break;
        }
    }

    @Override
    public Class<? extends MessageEmbed.AuthorInfo> getReturnType() {
        return MessageEmbed.AuthorInfo.class;
    }

    @Override
    protected String getPropertyName() {
        return "author of embed";
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the author of " + getExpr().toString(e, debug);
    }

}

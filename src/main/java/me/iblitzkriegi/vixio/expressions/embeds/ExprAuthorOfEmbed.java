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

public class ExprAuthorOfEmbed extends SimplePropertyExpression<EmbedBuilder, MessageEmbed.AuthorInfo> {


    static {
        Vixio.getInstance().registerPropertyExpression(ExprAuthorOfEmbed.class, MessageEmbed.AuthorInfo.class,
                "(author info|author)[s]", "embedbuilders")
                .setName("Author of Embed")
                .setDesc("Returns the author of an embed. Can be set to any author.")
                .setExample("set author of {_embed} to author named \"Pikachu\"");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public MessageEmbed.AuthorInfo convert(final EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getAuthor();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if ((mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    MessageEmbed.AuthorInfo.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setAuthor(null, null, null);
                return;

            case SET:
                MessageEmbed.AuthorInfo author = (MessageEmbed.AuthorInfo) delta[0];
                embed.setAuthor(author.getName(), author.getUrl(), author.getIconUrl());
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
    public String toString(final Event e, final boolean debug) {
        return "the author of " + getExpr().toString(e, debug);
    }

}

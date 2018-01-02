package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Title;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprTitleOfEmbed extends SimplePropertyExpression<EmbedBuilder, Title> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprTitleOfEmbed.class, Title.class,
                "title[s]", "embedbuilders")
                .setName("Title of Embed")
                .setDesc("Returns the title of an embed.")
                .setExample("set the embed title of {_embed} to title with text \"hey this is a cool title!\" and no icon");
    }


    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public Title convert(final EmbedBuilder embed) {
        if (embed.isEmpty()) return null;

        MessageEmbed builtEmbed = embed.build();
        return new Title(builtEmbed.getTitle(), builtEmbed.getUrl());
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    Title.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setTitle(null);
                return;

            case SET:
                Title title = (Title) delta[0];
                embed.setTitle(title.getText(), title.getUrl());

        }
    }

    @Override
    public Class<? extends Title> getReturnType() {
        return Title.class;
    }

    @Override
    protected String getPropertyName() {
        return "title of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the title of " + getExpr().toString(e, debug);
    }

}

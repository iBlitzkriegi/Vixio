package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.embed.Title;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprEmbedTitle extends SimplePropertyExpression<EmbedBuilder, Title> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedTitle.class, Title.class,
                "title", "embedbuilders")
                .setName("Title of Embed")
                .setDesc("Returns the title of an embed.")
                .setExample("set the embed title of {_embed} to title with text \"hey this is a cool title!\" and no icon");
    }


    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public Title convert(EmbedBuilder embed) {
        if (embed.isEmpty()) return null;

        MessageEmbed builtEmbed = embed.build();
        return new Title(builtEmbed.getTitle(), builtEmbed.getUrl());
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    String.class,
                    Title.class
            };
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) {
            return;
        }

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setTitle(null);
                return;

            case SET:
                Title title = delta[0] instanceof String ? new Title((String) delta[0], null) : (Title) delta[0];
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
    public String toString(Event e, boolean debug) {
        return "the title of " + getExpr().toString(e, debug);
    }

}

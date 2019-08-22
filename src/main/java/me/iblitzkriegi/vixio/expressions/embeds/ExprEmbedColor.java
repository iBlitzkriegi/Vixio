package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.Event;

import java.awt.Color;

public class ExprEmbedColor extends SimplePropertyExpression<EmbedBuilder, Color> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedColor.class, Color.class,
                "colo[u]r", "embedbuilders")
                .setName("Color of Embed")
                .setDesc("Returns the color of an embed. Can be set to any color (e.g. red).")
                .setExample("set discord color of {_embed} to red");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public Color convert(EmbedBuilder embed) {
        return new EmbedBuilder(embed).setDescription("filler").build().getColor();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if ((mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{Color.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setColor(null);
                return;

            case SET:
                embed.setColor((Color) delta[0]);
        }
    }

    @Override
    public Class<? extends Color> getReturnType() {
        return Color.class;
    }

    @Override
    protected String getPropertyName() {
        return "color of embed";
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the color of embed " + getExpr().toString(e, debug);
    }

}

package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.awt.Color;
import java.lang.reflect.Field;

public class ExprColorOfEmbed extends SimplePropertyExpression<EmbedBuilder, Color> {

    private static Field COLOR_FIELD;

    static {
        try {
            COLOR_FIELD = EmbedBuilder.class.getDeclaredField("color");
            COLOR_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Skript.error("Vixio couldn't find the color field in the EmbedBuilder class");
        }
        Vixio.getInstance().registerPropertyExpression(ExprColorOfEmbed.class, Color.class,
                "colo[u]r[s]", "embedbuilders")
                .setName("Color of Embed")
                .setDesc("Returns the color of an embed. Can be set to any color (e.g. red).")
                .setExample("set discord color of {_embed} to red");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return COLOR_FIELD != null;
    }

    @Override
    public Color convert(final EmbedBuilder embed) {
        if (embed.isEmpty()) {
            Color color = null;
            try {
                color = (Color) COLOR_FIELD.get(embed);
            } catch (IllegalAccessException e) {
                Skript.exception(e);
            }
            return color;
        }
        return embed.build().getColor();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if ((mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{Color.class};
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
    public String toString(final Event e, final boolean debug) {
        return "the color of embed " + getExpr().toString(e, debug);
    }

}

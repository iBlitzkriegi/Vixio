package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import org.bukkit.event.Event;

import java.awt.Color;


public class ExprColorFromRGB extends SimpleExpression<Color> {


    static {
        Vixio.getInstance().registerExpression(ExprColorFromRGB.class, Color.class, ExpressionType.SIMPLE,
                "[java[ ]]colo[u]r from rgb %number%(, | and )%number%(, | and )%number%")
                .setName("Color")
                .setDesc("A color that can be more specific that Skript's  color type.")
                .setExample("set {_color} to color from rgb 0, 0 and 0 # results in black",
                        "set {_color} to black # results in black",
                        "set {_color} to \"black\" parsed as a color  # results in black"
                );
    }

    private Expression<? extends Number> red, green, blue;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        red = (Expression<Number>) exprs[0];
        green = (Expression<Number>) exprs[1];
        blue = (Expression<Number>) exprs[2];
        return true;
    }

    @Override
    public Color[] get(Event e) {
        Number red = this.red.getSingle(e);
        Number green = this.green.getSingle(e);
        Number blue = this.blue.getSingle(e);

        if (red == null || green == null || blue == null) {
            return null;
        }

        return new Color[]{
                new Color(red.intValue(), green.intValue(), blue.intValue())
        };

    }

    @Override
    public Class<? extends Color> getReturnType() {
        return Color.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "color from rgb " + red.toString(e, debug) + ", " + green.toString(e, debug) + " and " + blue.toString(e, debug);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}

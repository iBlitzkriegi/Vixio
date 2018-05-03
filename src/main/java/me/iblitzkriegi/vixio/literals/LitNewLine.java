package me.iblitzkriegi.vixio.literals;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import org.bukkit.event.Event;


public class LitNewLine extends SimpleLiteral<String> {

    static {
        Vixio.getInstance().registerExpression(LitNewLine.class, String.class, ExpressionType.SIMPLE, "([a] new line|nl)");
    }

    public LitNewLine() {
        super(System.getProperty("line.separator"), true);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "new line";
    }

}

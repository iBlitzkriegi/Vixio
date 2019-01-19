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
        Vixio.getInstance().registerExpression(LitNewLine.class, String.class, ExpressionType.SIMPLE, "([a] new line|nl)")
                .setName("New Line")
                .setDesc("Get a newline, which means skips to a new line")
                .setExample(
                        "discord command $nl:",
                        "\ttrigger:",
                        "\t\tset {_m} to a message builder",
                        "\t\tappend \"Hey!\" to {_m}",
                        "\t\tappend \"%nl%\" to {_m}",
                        "\t\tappend \"There!\" to {_m}",
                        "\t\treply with {_m}"
                );
    }

    public LitNewLine() {
        super("\n", true);
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

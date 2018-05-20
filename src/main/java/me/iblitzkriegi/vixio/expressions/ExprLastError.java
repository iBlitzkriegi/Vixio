package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import org.bukkit.event.Event;

public class ExprLastError extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprLastError.class, String.class, ExpressionType.SIMPLE, "[the] last vixio error")
                .setName("Last vixio error")
                .setDesc("Set when vixio runs into a error, like a permission error.")
                .setExample("\t\tban event-user from event-guild\n" +
                        "\t\tset {var} to last vixio error\n" +
                        "\t\tif {var} is set:\n" +
                        "\t\t\tif {var} contains \"permission\":\n" +
                        "\t\t\treply with \"I tried to ban that user but didnt have the perms!\"");
    }

    public static String lastError;

    @Override
    protected String[] get(Event e) {
        if (lastError == null) {
            return null;
        }
        String error = lastError;
        lastError = null;
        return new String[]{error};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last vixio error";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

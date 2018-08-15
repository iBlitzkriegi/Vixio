package me.iblitzkriegi.vixio.literals;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

public class LitZeroWidthSpace extends SimpleLiteral<String> {

    static {
        Vixio.getInstance().registerExpression(LitZeroWidthSpace.class, String.class, ExpressionType.SIMPLE, "[a] zero width space")
                .setName("Zero width space")
                .setDesc("Get a zero width space")
                .setExample("append zero width space to {_messageBuilder}");
    }

    public LitZeroWidthSpace() {
        super(EmbedBuilder.ZERO_WIDTH_SPACE, true);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "zero width space";
    }

}

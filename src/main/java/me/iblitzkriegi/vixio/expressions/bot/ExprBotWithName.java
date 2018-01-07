package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Bot;
import org.bukkit.event.Event;

public class ExprBotWithName extends SimpleExpression<Bot> {
    static {
        Vixio.getInstance().registerExpression(ExprBotWithName.class, Bot.class, ExpressionType.SIMPLE, "bot named %string%")
                .setName("Bot with name")
                .setDesc("Get a %bot% type via the name you login with in the Connect effect.")
                .setExample("set {e} to bot named ");
    }
    private Expression<String> bot;
    @Override
    protected Bot[] get(Event e) {
        return new Bot[]{Vixio.getInstance().botNameHashMap.get(bot.getSingle(e))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Bot> getReturnType() {
        return Bot.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "bot with name " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<String>) exprs[0];
        return true;
    }
}

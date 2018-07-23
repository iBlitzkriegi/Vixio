package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import org.bukkit.event.Event;

public class ExprBot extends SimpleExpression<Bot> {
    static {
        Vixio.getInstance().registerExpression(ExprBot.class, Bot.class, ExpressionType.SIMPLE,
                "bot[s] [(with name|named)] %strings%")
                .setName("Bot named")
                .setDesc("Get a bot instance by its name.")
                .setExample("set the name of bot \"Jewel\" to \"Juul\"");
    }

    private Expression<String> name;

    @Override
    protected Bot[] get(Event e) {
        String name = this.name.getSingle(e);
        if (name == null || name.isEmpty()) {
            return null;
        }

        return new Bot[]{Vixio.getInstance().botNameHashMap.get(name)};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Bot> getReturnType() {
        return Bot.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "bot named " + name.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        return true;
    }
}

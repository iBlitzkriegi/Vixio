package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class ExprGuilds extends PropertyExpression<Bot, Guild> implements EasyMultiple<Bot, Guild> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGuilds.class, Guild.class,
                "guild", "bots")
                .setName("Guilds of bot")
                .setDesc("Get all the guilds of a bot")
                .setExample("set {var::*} to guilds of event-bot");
    }

    @Override
    protected Guild[] get(Event e, Bot[] source) {
        return convert(getReturnType(), getExpr().getAll(e), bot -> bot.getJDA().getGuilds().stream()
                .toArray(size -> new Guild[size])
        );
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the guilds of " + getExpr().toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Bot>) exprs[0]);
        return true;
    }
}

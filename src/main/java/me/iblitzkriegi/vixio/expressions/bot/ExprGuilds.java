package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class ExprGuilds extends SimpleExpression<Guild> implements EasyMultiple<Bot, Guild> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGuilds.class, Guild.class,
                "guild", "bot/string")
                .setName("Guilds of bot")
                .setDesc("Get all the guilds of a bot")
                .setExample(
                        ""
                );
    }

    private Expression<Object> bot;

    @Override
    protected Guild[] get(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot != null) {
            return bot.getJDA().getGuilds().toArray(new Guild[0]);
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return bot.toString(e, debug) + "'s guilds";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        return true;
    }
}

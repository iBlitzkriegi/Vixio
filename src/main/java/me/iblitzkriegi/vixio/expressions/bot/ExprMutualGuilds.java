package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Collection;

public class ExprMutualGuilds extends SimpleExpression<Guild> {
    static {
        Vixio.getInstance().registerExpression(ExprMutualGuilds.class, Guild.class, ExpressionType.SIMPLE,
                "[the] mutual guilds (of|with) %user% [and %bot/string%]", "%user%[[']s] and %bot/string%[[']s] mutual guilds")
                .setName("Mutual Guild of User")
                .setDesc("Get all the guilds a user and a bot share. The bot may be assumed in events.")
                .setExample(
                        "discord command $mutual <user>:",
                        "\ttrigger:",
                        "\t\treply with \"I share %size of mutual guilds of arg-1% guilds with %arg-1%\""
                );
    }

    private Expression<User> user;
    private Expression<Object> bot;

    @Override
    protected Guild[] get(Event e) {
        User user = this.user.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null || user == null) {
            return null;
        }
        Collection<Guild> mutualGuilds = bot.getShardMananger().getMutualGuilds(user);
        return mutualGuilds.toArray(new Guild[0]);
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
        return "mutual guilds of " + user.toString(e, debug) + " and " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<User>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}

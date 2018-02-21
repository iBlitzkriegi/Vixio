package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class ExprUserWithId extends SimpleExpression<User> {
    static {
        Vixio.getInstance().registerExpression(ExprUserWithId.class, User.class, ExpressionType.SIMPLE,
                "user with id %string%")
                .setName("User with ID")
                .setDesc("Get a User via their ID.")
                .setExample("broadcast name of user with id \"456145141891891\"");
    }

    private Expression<String> id;

    @Override
    protected User[] get(Event e) {
        String id = this.id.getSingle(e);
        Bot bot = Util.randomBot();
        if (id == null || bot == null) {
            return null;
        }

        return new User[]{bot.getJDA().getUserById(id)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "user with id " + id.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        return true;
    }
}

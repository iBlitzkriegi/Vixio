package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;

public class ExprAvatarOfUser extends SimpleExpression<Avatar> {
    static {
        Vixio.getInstance().registerExpression(ExprAvatarOfUser.class, Avatar.class, ExpressionType.SIMPLE,
                "[the] avatar [url[s]] of %users%", "[the] default avatar [url[s]] of %users%")
                .setName("Avatar url of User")
                .setDesc("Get either the user's custom avatar or their default one that discord gave them.")
                .setExample("Coming Soon.");
    }
    private Expression<User> users;
    private boolean custom;
    @Override
    protected Avatar[] get(Event e) {
        User[] users = this.users.getAll(e);
        if (users == null) {
            return null;
        }
        if (custom) {
            return Arrays.stream(users)
                    .filter(Objects::nonNull)
                    .map(user -> new Avatar(user, user.getAvatarUrl(), false))
                    .toArray(Avatar[]::new);
        }
        return Arrays.stream(users)
                .filter(Objects::nonNull)
                .map(user -> new Avatar(user, user.getDefaultAvatarUrl(), true))
                .toArray(Avatar[]::new);
    }

    @Override
    public boolean isSingle() {
        return users.isSingle();
    }

    @Override
    public Class<? extends Avatar> getReturnType() {
        return Avatar.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the " + (custom ? "default" : "custom") + " avatar urls of " + users.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        users = (Expression<User>) exprs[0];
        custom = matchedPattern == 0;
        return true;
    }
}

package me.iblitzkriegi.vixio.expressions.user;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import net.dv8tion.jda.core.entities.User;

public class ExprAvatar extends SimplePropertyExpression<User, Avatar> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprAvatar.class, Avatar.class,
                "[<default>] avatar", "users")
                .setName("Avatar")
                .setDesc("Get either the user's custom avatar or their default one that discord gave them. You can extract the id from the url using the ID expression.")
                .setExample("broadcast \"%avatar of user with id \"\"44950981891\"\"%\"");
    }

    private boolean custom;

    @Override
    public Avatar convert(User user) {
        return new Avatar(user, custom ? user.getAvatarUrl() : user.getDefaultAvatarUrl(), custom);
    }

    @Override
    public Class<? extends Avatar> getReturnType() {
        return Avatar.class;
    }

    @Override
    protected String getPropertyName() {
        return custom ? "avatar" : "default avatar";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<User>) exprs[0]);
        custom = parseResult.regexes.size() == 0;
        return true;
    }

}

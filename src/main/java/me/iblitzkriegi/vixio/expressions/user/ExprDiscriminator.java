package me.iblitzkriegi.vixio.expressions.user;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.User;

public class ExprDiscriminator extends SimplePropertyExpression<User, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprDiscriminator.class, String.class,
                "discriminator", "users")
                .setName("Discriminator of User")
                .setDesc("Get the discriminator of a user, this is the four numbers after a users name.")
                .setExample(
                        "discord command $info <user>:",
                        "\ttrigger:",
                        "\t\treply with \"%discriminator of arg-1%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "discriminator";
    }

    @Override
    public String convert(User user) {
        return user.getDiscriminator();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

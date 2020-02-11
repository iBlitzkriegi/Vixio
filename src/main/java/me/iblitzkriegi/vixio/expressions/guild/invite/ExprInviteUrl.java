package me.iblitzkriegi.vixio.expressions.guild.invite;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Invite;

public class ExprInviteUrl extends SimplePropertyExpression<Invite, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprInviteUrl.class, String.class, "invite url", "invite")
                .setName("Invite Url of")
                .setDesc("Get the Url of a Discord invite.")
                .setExample(
                        "discord command invite:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tcreate invite to event-channel:",
                        "\t\t\tset the max usage of the invite to 1",
                        "\t\t\tset {_} to the invite",
                        "\t\treply with \"Done! Created: %invite url of {_}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "invite url";
    }

    @Override
    public String convert(Invite invite) {
        return invite.getUrl();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

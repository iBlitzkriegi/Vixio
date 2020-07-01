package me.iblitzkriegi.vixio.expressions.guild.invite;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Invite;

public class ExprInviteTimeCreated extends SimplePropertyExpression<Invite, Date> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprInviteTimeCreated.class, Date.class, "[invite] creation date", "invite")
                .setName("Creation date of Invite")
                .setDesc("Get the time a discord invite was created.")
                .setExample(
                        "discord command rawr:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tcreate invite to event-channel with event-bot:",
                        "\t\t\tset max uses of the invite to 5",
                        "\t\t\tset {_} to the invite",
                        "\t\treply with \"%creation date of of {_}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "[invite] creation date";
    }

    @Override
    public Date convert(Invite invite) {
        return Util.getDate(invite.getTimeCreated());
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

}

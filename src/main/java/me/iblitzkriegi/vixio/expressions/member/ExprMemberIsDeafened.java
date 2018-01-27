package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Member;


public class ExprMemberIsDeafened extends SimplePropertyExpression<Member, Boolean> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMemberIsDeafened.class, Boolean.class, "deafened state", "members")
                .setName("Deafened State of Member")
                .setDesc("Get the Deafened state of a Member in the shape of a Boolean.")
                .setExample(
                        "command /isDeaf <text> <text>:",
                        "\ttrigger:",
                        "\t\tset {member} to member of user with id arg-1 in guild with id arg-2",
                        "\t\tif {member} is set:",
                        "\t\t\tbroadcast \"%deafened state of {member}%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "deafened state";
    }

    @Override
    public Boolean convert(Member member) {
        return member.getVoiceState().isDeafened();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}

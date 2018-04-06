package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;

public class ExprOnlineStatus extends SimplePropertyExpression<Member, OnlineStatus> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprOnlineStatus.class, OnlineStatus.class,
                "[online][(-| )]status", "members")
                .setName("Online status of Member")
                .setDesc("Get a members current online status.")
                .setExample("member with id \"1651561616\" in guild with id \"651115615\"'s online status");
    }

    @Override
    protected String getPropertyName() {
        return "[online][(-| )]status";
    }

    @Override
    public OnlineStatus convert(Member member) {
        return member.getOnlineStatus();
    }

    @Override
    public Class<? extends OnlineStatus> getReturnType() {
        return OnlineStatus.class;
    }
}

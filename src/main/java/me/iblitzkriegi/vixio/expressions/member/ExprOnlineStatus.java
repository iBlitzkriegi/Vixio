package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class ExprOnlineStatus extends SimplePropertyExpression<Object, OnlineStatus> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprOnlineStatus.class, OnlineStatus.class,
                "[online][(-| )]status", "members/users")
                .setName("Online status of User")
                .setDesc("Get a members current online status.")
                .setExample("member with id \"1651561616\" in guild with id \"651115615\"'s online status");
    }

    @Override
    protected String getPropertyName() {
        return "[online][(-| )]status";
    }

    @Override
    public OnlineStatus convert(Object object) {
        if (object instanceof Member) {
            return ((Member)object).getOnlineStatus();
        } else if (object instanceof User) {
            Member member = Util.getMemberFromUser((User) object);
            return member == null ? null : member.getOnlineStatus();
        }
        return null;
    }

    @Override
    public Class<? extends OnlineStatus> getReturnType() {
        return OnlineStatus.class;
    }
}

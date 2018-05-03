package me.iblitzkriegi.vixio.expressions.user;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class ExprGame extends SimplePropertyExpression<Object, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprGame.class, String.class,
                "game", "members/users")
                .setName("Game of")
                .setDesc("Get the game of a user")
                .setExample("game of user with id \"4165651561561\"");
    }

    @Override
    protected String getPropertyName() {
        return "game";
    }

    @Override
    public String convert(Object object) {
        if (object instanceof Member) {
            return ((Member) object).getGame().getName();
        } else if (object instanceof User) {
            Member member = Util.getMemberFromUser((User) object);
            if (member == null) {
                return null;
            }

            return member.getGame() == null ? null : member.getGame().getName();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

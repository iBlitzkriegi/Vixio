package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class ExprJoinDate extends SimplePropertyExpression<Object, Date> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprJoinDate.class, Date.class, "[<discord>] join date", "members/users")
                .setName("Join Date of Member")
                .setDesc("Get the date a member joined a guild.")
                .setExample("reply with \"%join date of event-member%\"");
    }

    private boolean discord;

    @Override
    protected String getPropertyName() {
        return "join date";
    }

    @Override
    public Date convert(Object object) {
        if (object instanceof Member) {
            Member member = (Member) object;
            return discord ? Util.getDate(member.getJoinDate()) : Util.getDate(member.getUser().getCreationTime());
        } else if (object instanceof User) {
            return discord ? null : Util.getDate(((User)object).getCreationTime());
        }
        return null;
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        setExpr(exprs[0]);
        discord = parseResult.regexes.size() == 0;
        return true;
    }

}

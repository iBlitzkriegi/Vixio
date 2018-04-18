package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class ExprOnlineStatus extends SimplePropertyExpression<Object, OnlineStatus> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprOnlineStatus.class, OnlineStatus.class,
                "[online][(-| )]status", "members/users/bots/strings")
                .setName("Online status of")
                .setDesc("Get a users online status of a user/member. Or the online status of a bot, which can be set.")
                .setExample("set status of event-bot to do not disturb");
    }

    @Override
    protected String getPropertyName() {
        return "[online][(-| )]status";
    }

    @Override
    public OnlineStatus convert(Object object) {
        if (object instanceof String || object instanceof Bot) {
            Bot bot = Util.botFrom(object);
            if (bot != null) {
                return bot.getJDA().getPresence().getStatus();
            }
        } else if (object instanceof User) {
            Member member = Util.getMemberFromUser(object);
            if (member != null) {
                return member.getOnlineStatus();
            }
        } else if (object instanceof Member) {
            return ((Member) object).getOnlineStatus();
        }
        return null;
    }

    @Override
    public Class<? extends OnlineStatus> getReturnType() {
        return OnlineStatus.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{OnlineStatus.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Object input : getExpr().getAll(e)) {
            if (input instanceof Bot || input instanceof String) {
                Bot bot = Util.botFrom(input);
                if (bot != null) {
                    bot.getJDA().getPresence().setStatus((OnlineStatus) delta[0]);
                }
            }
        }
    }
}

package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

public class ExprOnlineStatus extends SimplePropertyExpression<Object, OnlineStatus> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprOnlineStatus.class, OnlineStatus.class,
                "[online][(-| )]status", "members/users/bots/strings")
                .setName("Online status of")
                .setDesc("Get the online status of a user or a member. The online status can be set or reset for a bot.")
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
                return bot.getJDA().getShards().get(0).getPresence().getStatus();
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
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Object input : getExpr().getAll(e)) {
            if (input instanceof Bot || input instanceof String) {
                Bot bot = Util.botFrom(input);
                if (bot != null) {
                    bot.getJDA().setStatus((OnlineStatus) delta[0]);
                }
            }
        }
    }
}

package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.changers.EffChange;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;


public class ExprNicknameOfMember extends ChangeableSimplePropertyExpression<Member, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprNicknameOfMember.class, String.class, "nickname",
                "members")
                .setName("Nickname of member")
                .setDesc("Get the nickname of a Member, if they have no this returns their username. To modify their nickname you must include a bot. Moreover, if there is no nickname it returns their username. Changers: SET")
                .setExample("set nickname of event-member to \"new nickname\" with event-bot");
    }

    @Override
    public String convert(Member member) {
        return member.getNickname();
    }

    @Override
    public String getPropertyName() {
        return "nickname";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, Bot bot, final Changer.ChangeMode mode) {
        Member[] members = getExpr().getAll(e);
        if (members == null || members.length == 0) {
            return;
        }

        for (Member member : members) {
            Guild bindedGuild = Util.bindGuild(bot, member.getGuild());
            if (bindedGuild == null) {
                continue;
            }

            try {
                if (mode == Changer.ChangeMode.SET) {
                    if (delta[0] == null || ((String) delta[0]).isEmpty()) {
                        return;
                    }
                    bindedGuild.getController().setNickname(member, (String) delta[0]).queue();
                } else {
                    bindedGuild.getController().setNickname(member, null).queue();
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, EffChange.format(mode, "nickname of", getExpr(), bot), x.getPermission().getName());
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "nickname of " + getExpr().toString(e, debug);
    }

}

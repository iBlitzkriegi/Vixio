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


public class ExprNickname extends ChangeableSimplePropertyExpression<Member, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprNickname.class, String.class, "discord nickname",
                "members")
                .setName("Nickname of")
                .setDesc("Gets a member's nickname (guild sensitive name). You can set this expression.")
                .setExample("set the discord nickname of event-member to \"new nickname\" with event-bot");
    }

    @Override
    public String convert(Member member) {
        return member.getEffectiveName();
    }

    @Override
    public String getPropertyName() {
        return "discord nickname";
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
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        Member[] members = getExpr().getAll(e);
        if (members == null || members.length == 0) {
            return;
        }

        for (Member member : members) {
			Guild boundGuild = Util.bindGuild(bot, member.getGuild());
			if (boundGuild == null) {
                continue;
            }

            try {
                if (mode == Changer.ChangeMode.SET) {
                    if (delta[0] == null || ((String) delta[0]).isEmpty()) {
                        return;
                    }
					boundGuild.getController().setNickname(member, (String) delta[0]).queue();
                } else {
					boundGuild.getController().setNickname(member, null).queue();
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, EffChange.format(mode, "nickname of", getExpr(), bot), x.getPermission().getName());
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "discord nickname of " + getExpr().toString(e, debug);
    }

}

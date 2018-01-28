package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;


public class ExprNicknameOfMember extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprNicknameOfMember.class, String.class, ExpressionType.SIMPLE,
                "nickname of %members% [(with|as) %-bot/string%]")
                .setName("Nickname of member")
                .setDesc("Get the nickname of a Member, if they have no this returns their username. To modify their nickname you must include a bot. Moreover, if there is no nickname it returns their username. Changers: SET")
                .setExample("set nickname of event-member as event-bot to \"XD\"");
    }

    private Expression<Member> members;
    private Expression<Object> bot;

    @Override
    protected String[] get(Event e) {
        Member[] members = this.members.getAll(e);
        if (members == null) {
            return null;
        }

        return Arrays.stream(members)
                .filter(Objects::nonNull)
                .map(Member::getEffectiveName)
                .toArray(String[]::new);
    }

    @Override
    public boolean isSingle() {
        return members.isSingle();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)
            return new Class[]{String.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("set nickname of member");
            return;
        }
        String nickname = mode == Changer.ChangeMode.SET ? (String) delta[0] : null;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Member[] members = this.members.getAll(e);
        if (bot == null || members == null) {
            return;
        }

        for (Member member : members) {
            Guild bindedGuild = Util.bindGuild(bot, member.getGuild());
            if (bindedGuild != null) {
                try {
                    bindedGuild.getController().setNickname(member, nickname).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set nickname", x.getPermission().getName());
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "nickname of " + members.toString(e, debug) + (bot == null ? "" : " as " + bot.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

}

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
        Vixio.getInstance().registerExpression(ExprNicknameOfMember.class, String.class, ExpressionType.SIMPLE, "nickname of %members% [(with|as)] [%bot/string%]")
                .setName("Nickname of member")
                .setDesc("Get the nickname of a Member, if they have no this returns their username. Has a set changer which to use must include the bot.")
                .setExample("Coming soon.");
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
                .map(member1 -> member1.getEffectiveName())
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
            return new Class[] {String.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        String nickname = mode == Changer.ChangeMode.SET ? (String) delta[0] : null;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            Vixio.getErrorHandler().cantFindBot((String) this.bot.getSingle(e), "set nickname of member");
            return;
        }
        Member[] members = this.members.getAll(e);
        if (members == null) {
            return;
        }
        for (Member member : members) {
            Guild guild = member.getGuild();
            try {
                if (Util.botIsConnected(bot, guild.getJDA())) {
                    guild.getController().setNickname(member, nickname).queue();
                } else {
                    Guild bindingGuild = bot.getJDA().getGuildById(guild.getId());
                    if (bindingGuild != null) {
                        bindingGuild.getController().setNickname(member, nickname).queue();
                    }
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set nickname", x.getPermission().getName());
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "nickname of " + members.toString(e, debug) + (bot == null ? "" : " as " + bot.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

}

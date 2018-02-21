package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class ExprMember extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerExpression(ExprMember.class, Member.class, ExpressionType.SIMPLE,
                "%user% in %guild%")
                .setName("User in Guild")
                .setDesc("Returns the member form of a user in the specified guild")
                .setExample("broadcast nickname of user with id \"1561515615610515\" in event-guild");
    }

    private Expression<User> user;
    private Expression<Guild> guild;

    @Override
    protected Member[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        User user = this.user.getSingle(e);
        if (guild == null || user == null) {
            return null;
        }
        return new Member[]{guild.getMember(user)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return user.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<User>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

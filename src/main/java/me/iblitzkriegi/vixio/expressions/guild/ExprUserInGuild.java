package me.iblitzkriegi.vixio.expressions.guild;

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

public class ExprUserInGuild extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerExpression(ExprUserInGuild.class, Member.class, ExpressionType.SIMPLE,
                "[member of] %user% in %guild%")
                .setName("Member of User in Guild")
                .setDesc("Get the Member of a User in a Guild")
                .setExample(
                        "on guild message received:",
                        "\tset {member} to member of event-user in event-guild",
                        "\treply with \"%name of {member}%\""
                );
    }

    private Expression<User> user;
    private Expression<Guild> guild;

    @Override
    protected Member[] get(Event e) {
        User user = this.user.getSingle(e);
        Guild guild = this.guild.getSingle(e);
        if (user == null || guild == null) {
            return null;
        }
        return new Member[]{guild.getMemberById(user.getId())};
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
        return "member of " + user.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<User>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}

package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

import java.util.List;

public class ExprMembers extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMembers.class, Member.class,
                "discord member", "guild/category")
                .setName("Members of")
                .setDesc("Get all of the Member from a variety of different types.")
                .setExample(
                        "discord command $guild:",
                        "\ttrigger:",
                        "\t\treply with \"%size of members of event-guild%\""
                );
    }

    private Expression<Object> object;

    @Override
    protected Member[] get(Event e) {
        Object object = this.object.getSingle(e);

        if (object instanceof Category) {
            List<Member> members = ((Category) object).getMembers();

            return members.toArray(new Member[members.size()]);
        } else if (object instanceof Guild) {
            List<Member> members = ((Guild) object).getMembers();

            return members.toArray(new Member[members.size()]);
        }

        return null;

    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "members of " + object.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }
}

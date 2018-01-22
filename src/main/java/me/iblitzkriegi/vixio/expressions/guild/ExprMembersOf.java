package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class ExprMembersOf extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerExpression(ExprMembersOf.class, Member.class, ExpressionType.SIMPLE, "members of %guild/category%")
                .setName("Members of Object")
                .setDesc("Get all of the members of a object.")
                .setExample("Coming Soon!");
    }
    private Expression<Object> object;
    @Override
    protected Member[] get(Event e) {
        Object object = this.object.getSingle(e);
        if (object == null) {
            return null;
        }
        if (object instanceof Category) {
            return ((Category) object).getMembers().toArray(new Member[((Category) object).getMembers().size()]);
        } else if (object instanceof Guild) {
            return ((Guild) object).getMembers().toArray(new Member[((Guild) object).getMembers().size()]);
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

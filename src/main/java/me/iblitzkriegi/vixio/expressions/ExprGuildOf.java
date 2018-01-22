package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

public class ExprGuildOf extends SimplePropertyExpression<Object, Guild> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGuildOf.class, Guild.class, "guild", "category/message")
                .setName("Guild of")
                .setDesc("Get the guild of one of the types in the syntax.")
                .setExample("Coming Soon");
    }
    @Override
    protected String getPropertyName() {
        return "guild of";
    }

    @Override
    public Guild convert(Object o) {
        if (o instanceof Category) {
            return ((Category) o).getGuild();
        } else if (o instanceof Message) {
            return ((Message) o).getGuild();
        }
        return null;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }
}

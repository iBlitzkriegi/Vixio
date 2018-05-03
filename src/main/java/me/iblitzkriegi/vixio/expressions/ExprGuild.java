package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

public class ExprGuild extends SimplePropertyExpression<Object, Guild> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGuild.class, Guild.class, "guild", "category/message")
                .setName("Guild")
                .setDesc("Get the guild of something.")
                .setExample("reply with \"%name of guild of event-message%\"");
    }

    @Override
    protected String getPropertyName() {
        return "guild";
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

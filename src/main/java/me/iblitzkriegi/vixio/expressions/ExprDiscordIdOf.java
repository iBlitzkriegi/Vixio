package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.ISnowflake;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprDiscordIdOf extends SimplePropertyExpression<Object, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprDiscordIdOf.class, String.class, "id", "channel/guild/user/message/bot/role")
            .setName("Discord ID of")
            .setDesc("Get the ID of a discord object")
            .setExample("discord id of event-user");
    }
    @Override
    protected String getPropertyName() {
        return "id of";
    }

    @Override
    public String convert(Object o) {
        if(o instanceof ISnowflake) {
            return ((ISnowflake) o).getId();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

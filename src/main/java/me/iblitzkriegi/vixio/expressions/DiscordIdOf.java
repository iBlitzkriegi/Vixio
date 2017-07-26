package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.ISnowflake;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Blitz on 7/26/2017.
 */
public class DiscordIdOf extends SimplePropertyExpression<Object, String> {
    static {
        Vixio.registerPropertyExpression(DiscordIdOf.class, String.class, "discord id", "objects")
            .setName("ID of")
            .setDesc("Get the ID of a discord object")
            .setExample("discord id of event-user");
    }
    @Override
    protected String getPropertyName() {
        return "discord id of";
    }

    @Override
    public String convert(Object o) {
        if(o instanceof ISnowflake){
            return ((ISnowflake) o).getId();
        }
        Skript.error("Could not parse provided argument, please refer to the syntax.");
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

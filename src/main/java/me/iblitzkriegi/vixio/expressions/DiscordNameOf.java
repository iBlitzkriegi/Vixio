package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Blitz on 7/25/2017.
 */
public class DiscordNameOf extends SimplePropertyExpression<Object, String>{
    static {
        Vixio.registerPropertyExpression(DiscordNameOf.class, String.class,"discord name", "objects")
                .setName("Name of")
                .setDesc("Get the name of something/someone")
                .setExample("discord username of event-user");
    }

    @Override
    protected String getPropertyName() {
        return "discord name of";
    }

    @Override
    public String convert(Object o) {
        if(o instanceof User){
            return ((User) o).getName();
        }else if(o instanceof Guild){
            return ((Guild) o).getName();
        }else if(o instanceof TextChannel){
            return ((TextChannel) o).getName();
        }
        Skript.error("Could not parse provided argument, please refer to the syntax.");
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

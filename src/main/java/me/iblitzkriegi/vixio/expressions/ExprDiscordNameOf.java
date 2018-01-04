package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprDiscordNameOf extends SimplePropertyExpression<Object, String>{
    static {
        Vixio.getInstance().registerPropertyExpression(ExprDiscordNameOf.class, String.class,"discord name", "channel/guild/user/member/bot")
                .setName("Discord Name of")
                .setDesc("Get the name of something/someone")
                .setExample("discord name of event-user");
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
            System.out.println("Now im in the name of");
            return ((Guild) o).getName();
        }else if(o instanceof Channel){
            return ((Channel) o).getName();
        }else if(o instanceof Member){
            return ((Member) o).getUser().getName();
        }else if(o instanceof Bot){
            return ((Bot) o).getSelfUser().getName();
        }
        Skript.error("Could not parse provided argument, please refer to the syntax.");
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.*;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprDiscordNameOf extends SimplePropertyExpression<Object, String>{
    static {
        Vixio.getInstance().registerPropertyExpression(ExprDiscordNameOf.class, String.class,"name", "channel/guild/user/member/bot")
                .setName("Name of")
                .setDesc("Get the name of something/someone")
                .setExample("name of event-user");
    }

    @Override
    protected String getPropertyName() {
        return "name of";
    }

    @Override
    public String convert(Object o) {
        if(o instanceof User){
            return ((User) o).getName();
        }else if(o instanceof Guild){
            return ((Guild) o).getName();
        }else if(o instanceof MessageChannel){
            return ((MessageChannel) o).getName();
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

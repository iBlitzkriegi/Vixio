package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprMentionTagOf extends SimplePropertyExpression<Object, String>{
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionTagOf.class, String.class, "mention tag", "user/channel/member/bot")
                .setName("Discord Name of")
                .setDesc("Get the name of something/someone")
                .setExample("discord name of event-user");
    }

    @Override
    protected String getPropertyName() {
        return "mention tag of";
    }

    @Override
    public String convert(Object o) {
        if(o instanceof User){
            return ((User) o).getAsMention();
        }else if(o instanceof TextChannel){
            return ((TextChannel) o).getAsMention();
        }else if(o instanceof Member){
            return ((Member) o).getAsMention();
        }else if(o instanceof User){
            ((User) o).getAsMention();
        }else if(o instanceof Bot){
            return ((Bot) o).getSelfUser().getAsMention();
        }
        Skript.error("Could not parse provided argument, please refer to the syntax.");
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

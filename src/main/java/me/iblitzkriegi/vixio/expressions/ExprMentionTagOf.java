package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.IMentionable;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprMentionTagOf extends SimplePropertyExpression<Object, String>{
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionTagOf.class, String.class, "mention tag", "user/textchannel/member/bot/role")
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
        return ((IMentionable)o).getAsMention();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

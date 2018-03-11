package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Emoji;
import net.dv8tion.jda.core.entities.IMentionable;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprMentionTag extends SimplePropertyExpression<Object, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionTag.class, String.class, "mention tag", "user/textchannel/member/emoji/role")
                .setName("Mention of")
                .setDesc("Get the mention tag of something")
                .setExample("reply with mention tag of event-user");
    }

    @Override
    protected String getPropertyName() {
        return "mention tag";
    }

    @Override
    public String convert(Object o) {
        if (o instanceof IMentionable) {
            return ((IMentionable) o).getAsMention();
        } else if (o instanceof Emoji) {
            if (((Emoji) o).isEmote()) {
                return ((Emoji) o).getMention();
            }
            return ((Emoji) o).getName();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

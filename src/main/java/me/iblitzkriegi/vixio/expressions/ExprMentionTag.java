package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.IMentionable;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprMentionTag extends SimplePropertyExpression<Object, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprMentionTag.class, String.class, "mention tag", "users/channel/members/emotes/roles")
                .setName("Mention of")
                .setDesc("Get the mention tag of something")
                .setUserFacing("users/textchannels/members/emotes/roles")
                .setExample("reply with mention tag of event-user");
    }

    @Override
    protected String getPropertyName() {
        return "mention tag";
    }

    @Override
    public String convert(Object o) {
        if (o instanceof Channel) {
            if (!(((Channel) o).getType() == ChannelType.TEXT)) {
                return null;
            }
            return ((TextChannel) o).getAsMention();

        }
        return ((IMentionable) o).getAsMention();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

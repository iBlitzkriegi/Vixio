package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprId extends SimplePropertyExpression<Object, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprId.class, String.class, "discord id", "channel/guild/bot/user/message/role/avatar/category/member/emote/attachment")
                .setName("ID of")
                .setDesc("Get the ID of any discord entity.")
                .setExample("reply with \"%id of event-user%\"");
    }

    @Override
    protected String getPropertyName() {
        return "discord id";
    }

    @Override
    public String convert(Object o) {
        if (o instanceof ISnowflake) {
            return ((ISnowflake) o).getId();
        } else if (o instanceof UpdatingMessage) {
            return ((UpdatingMessage) o).getMessage().getId();
        } else if (o instanceof Avatar) {
            Avatar avatar = (Avatar) o;
            return avatar.isDefault() ? avatar.getUser().getDefaultAvatarId() : avatar.getUser().getAvatarId();
        } else if (o instanceof Member) {
            return ((Member) o).getUser().getId();
        } else if (o instanceof Emote) {
            return ((Emote) o).getID();
        } else if (o instanceof Message.Attachment) {
            return ((Message.Attachment) o).getId();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import net.dv8tion.jda.core.entities.ISnowflake;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprId extends SimplePropertyExpression<Object, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprId.class, String.class, "id", "channel/guild/bot/user/message/role/avatar/category")
                .setName("ID")
                .setDesc("Get the ID of something")
                .setExample("reply with \"%id of event-user%\"");
    }

    @Override
    protected String getPropertyName() {
        return "id";
    }

    @Override
    public String convert(Object o) {
        if (o instanceof ISnowflake) {
            return ((ISnowflake) o).getId();
        } else if (o instanceof Avatar) {
            Avatar avatar = (Avatar) o;
            return avatar.isDefault() ? avatar.getUser().getDefaultAvatarId() : avatar.getUser().getAvatarId();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

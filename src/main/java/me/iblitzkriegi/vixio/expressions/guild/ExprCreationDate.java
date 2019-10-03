package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.api.entities.ISnowflake;


public class ExprCreationDate extends SimplePropertyExpression<Object, Date> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprCreationDate.class, Date.class,
                "discord creation date", "channel/guild/message/role/category/emote/attachment")
                .setName("Discord Creation Date of")
                .setDesc("Get the creation date of most things in Discord.")
                .setExample("broadcast \"%discord creation date of event-guild%\"");
    }

    @Override
    protected String getPropertyName() {
        return "discord creation date";
    }

    @Override
    public Date convert(Object o) {
        if (o instanceof ISnowflake) {
            return Util.getDate(((ISnowflake) o).getTimeCreated());
        } else if (o instanceof UpdatingMessage) {
            return Util.getDate(((UpdatingMessage) o).getMessage().getTimeCreated());
        }
        return null;
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }
}

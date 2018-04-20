package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;

public class ExprIcon extends SimplePropertyExpression<Guild, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprIcon.class, String.class, "icon", "guilds")
                .setName("Icon URl of Guild")
                .setDesc("Get a guilds icon url.")
                .setExample("reply with \"%icon of event-guild%\"");
    }

    @Override
    protected String getPropertyName() {
        return "icon";
    }

    @Override
    public String convert(Guild guild) {
        return guild.getIconUrl();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

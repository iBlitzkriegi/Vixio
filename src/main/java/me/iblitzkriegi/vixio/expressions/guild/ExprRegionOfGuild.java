package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;

public class ExprRegionOfGuild extends SimplePropertyExpression<Guild, String> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprRegionOfGuild.class, String.class, "region", "guild")
                .setName("Region of Guild")
                .setDesc("Get the current region of a Guild.")
                .setExample("broadcast \"%region of event-guild%\"");
    }
    @Override
    protected String getPropertyName() {
        return "region of";
    }

    @Override
    public String convert(Guild guild) {
        return guild.getRegion().getName();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}

package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;

public class ExprPublicRole extends SimplePropertyExpression<Guild, Role> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprPublicRole.class, Role.class, "public role", "guild")
                .setName("Public Role of Guild")
                .setDesc("Get the public role in a guild. This is the everyone role.")
                .setExample(
                        "discord command role:",
                        "\ttrigger:",
                        "\t\treply with \"`%public role of event-guild%`\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "public role";
    }

    @Override
    public Role convert(Guild guild) {
        return guild.getPublicRole();
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }
}

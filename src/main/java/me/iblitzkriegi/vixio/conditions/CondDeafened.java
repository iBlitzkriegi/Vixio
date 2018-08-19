package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Member;

public class CondDeafened extends PropertyCondition<Member> {

    static {
        Vixio.getInstance().registerCondition(CondDeafened.class,
                "[(1¦guild)] deafened", "members")
                .setName("Member Is Deafened")
                .setDesc("If the \"guild\" modifier is included, this passes if the member is deafened via a guild admin." +
                        "If it isn't included, it passes if the user has either deafened themselves, or was deafened by an admin")
                .setExample(
                        "discord command $checkGuildMute <member>:",
                        "\ttrigger:",
                        "\t\tif arg-1 is guild muted:",
                        "\t\t\treply with \"%arg-1% is guild muted!\"",
                        "\t\t\tstop",
                        "\t\treply with \"%arg-1% is not guild muted!\""
                );
    }

    private boolean guild;

    @Override
    public boolean check(Member m) {
        return guild ? m.getVoiceState().isGuildDeafened() : m.getVoiceState().isDeafened();
    }

    @Override
    protected String getPropertyName() {
        return guild ? "guild deafened" : "deafened";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        guild = parseResult.mark == 1;
        return super.init(exprs, matchedPattern, isDelayed, parseResult);
    }
}

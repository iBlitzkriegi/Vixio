package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class CondMuted extends PropertyCondition<Member> {

    static {
        Vixio.getInstance().registerPropertyCondition(CondMuted.class,
                "[(1¦guild)] muted", "members")
                .setName("Member Is Muted")
                .setDesc("If the \"guild\" modifier is included, this passes if the member is muted via a guild admin." +
                        "If it isn't included, it passes if the user has either muted themselves, or was muted by an admin")
                .setExample(
                        "discord command $checkGuildMute <member>:,",
                        "\ttrigger:,",
                        "\t\tif arg-1 is guild muted:,",
                        "\t\t\treply with \"%arg-1% is guild muted!\",",
                        "\t\t\tstop,",
                        "\t\treply with \"%arg-1% is not guild muted!\""
                );
    }

    private boolean guild;

    @Override
    public boolean check(Member member) {
        return guild ? member.getVoiceState().isGuildMuted() : member.getVoiceState().isMuted();
    }

    @Override
    protected String getPropertyName() {
        return guild ? "guild muted" : "muted";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        guild = parseResult.mark == 1;
        return super.init(exprs, matchedPattern, isDelayed, parseResult);
    }
}

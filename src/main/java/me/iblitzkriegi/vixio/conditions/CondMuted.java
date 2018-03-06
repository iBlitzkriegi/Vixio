package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class CondMuted extends Condition implements EasyMultiple<Member, Void> {

    static {
        Vixio.getInstance().registerCondition(CondMuted.class,
                "%members% (is|are) [<guild>] muted", "%members% (is|are)(n't| not) [<guild>] muted")
                .setName("Member is muted")
                .setDesc("If the \"guild\" modifier is included, this passes if the member is muted via a guild admin." +
                        "If it isn't included, it passes if the user has either muted themselves, or was muted by an admin")
                .setExample(
                        "discord command checkGuildMute <text> <text>:",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tset {member} to user with id arg-1 in event-guild",
                        "\t\tif {member} is guild muted:",
                        "\t\t\tbroadcast \"%{member}% is guild muted!\""
                );
    }

    private Expression<Member> members;
    private boolean guild;

    @Override
    public boolean check(Event e) {
        return check(members.getAll(e),
                m -> guild ? m.getVoiceState().isGuildMuted() : m.getVoiceState().isMuted(),
                isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return members.toString(e, debug) + (isNegated() ? " is not" : " is ") + "guild muted";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        guild = parseResult.regexes.size() == 1;
        setNegated(matchedPattern == 1);
        return true;
    }

}

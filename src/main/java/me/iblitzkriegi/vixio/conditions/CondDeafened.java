package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

public class CondDeafened extends Condition implements EasyMultiple<Member, Void> {

    static {
        Vixio.getInstance().registerCondition(CondDeafened.class,
                "%members% (is|are) [<guild>] deafened", "%members% (is|are)(n't| not) [<guild>] deafened")
                .setName("Member is deafened")
                .setDesc("If the \"guild\" modifier is included, this passes if the member is deafened via a guild admin." +
                        "If it isn't included, it passes if the user has either deafened themselves, or was deafened by an admin")
                .setExample(
                        "discord command checkGuildDeaf <text> <text>:",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tset {member} to user with id arg-1 in event-guild",
                        "\t\tif {member} is guild deafened:",
                        "\t\t\tbroadcast \"%{member}% is guild deafened!\""
                );
    }

    private Expression<Member> members;
    private boolean guild;

    @Override
    public boolean check(Event e) {
        return check(members.getAll(e),
                m -> guild ? m.getVoiceState().isGuildDeafened() : m.getVoiceState().isDeafened(),
                isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return members.toString(e, debug) + (isNegated() ? " is not" : " is ") + (guild ? "guild " : "") + "deafened";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        guild = parseResult.regexes.size() == 1;
        setNegated(matchedPattern == 1);
        return true;
    }

}

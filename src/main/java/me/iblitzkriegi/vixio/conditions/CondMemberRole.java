package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

public class CondMemberRole extends Condition {
    static {
        Vixio.getInstance().registerCondition(CondMemberRole.class,
                "%member% (has|have) [the] role %role%", "%member% (does[n[']t]|does not) have [the] role %role%",
                "%member% (has|have) [the] role [named] %string%", "%member% (does[n[']t]|does not) have [the] role [named] %string%")
                .setName("Member has role")
                .setDesc("Check if a member either does, or does not have either a specific %role$ or a role with a certain name.")
                .setExample(
                        "discord command $role <member> <string>:",
                        "\ttrigger:",
                        "\t\tif arg-1 has role role named arg-2:",
                        "\t\t\treply with \"%arg-1% does have a role named %arg-2%\"",
                        "\t\t\tstop",
                        "\t\treply with \"%arg-1% does not have a role named %arg-2%\""
                );
    }

    private Expression<Member> member;
    private Expression<Object> role;
    private boolean not;
    private boolean roleName;

    @Override
    public boolean check(Event e) {
        Member member = this.member.getSingle(e);
        Object role = this.role.getSingle(e);
        if (role == null || member == null) {
            return false;
        }
        if (roleName) {
            String name = (String) role;
            for (Role memberRole : member.getRoles()) {
                if (memberRole.getName().equalsIgnoreCase(name)) {
                    return not == true;
                }
            }
            return not == false;
        }
        return not == member.getRoles().contains(role);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return member.toString(e, debug) + (not ? " has " : " does not have ") + (roleName ? "the role named " : "") + role.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        member = (Expression<Member>) exprs[0];
        role = (Expression<Object>) exprs[1];
        roleName = matchedPattern == 2 || matchedPattern == 3;
        not = matchedPattern == 0 || matchedPattern == 2;
        return true;
    }
}

package me.iblitzkriegi.vixio.conditions;

import java.util.Arrays;
import java.util.stream.Stream;

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
                "%members% (has|have) [the] role[s] %roles%",
                "%members% (don't|do not|doesn't|does not) have [the] role[s] %roles%",
                "%members% (has|have) [the] role[s] [named] %strings%",
                "%members% (don't|do not|doesn't|does not) have [the] role[s] [named] %strings%")
                .setName("Member has role")
                .setDesc("Check if a member either does, or does not have either a specific %role$ or a role with a certain name.")
                .setExample(
                        "discord command $role <member> <string>:",
                        "\ttrigger:",
                        "\t\tif arg-1 has role named arg-2:",
                        "\t\t\treply with \"%arg-1% does have a role named %arg-2%\"",
                        "\t\t\tstop",
                        "\t\treply with \"%arg-1% does not have a role named %arg-2%\""
                );
    }

    private Expression<Member> members;
    private Expression<Object> roles;
    private boolean roleName;

    // TODO test this
    @Override
    public boolean check(Event e) {
        String[] roleNames = Arrays.stream(roles.getArray(e))
                .map(role -> role instanceof Role ? ((Role) role).getName() : role)
                .toArray(String[]::new);

        Stream<String> memberRoles;
        for (Member member : members.getArray(e)) {
            for (String name : roleNames) {
                memberRoles = member.getRoles()
                        .stream()
                        .map(Role::getName);

                if (memberRoles == null)
                    return false;

                if (!memberRoles.anyMatch(name::equalsIgnoreCase) && !isNegated())
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return members.toString(e, debug) + (isNegated() ? " has " : " does not have ") + (roleName ? "the roles named " : "") + roles.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        roles = (Expression<Object>) exprs[1];
        roleName = matchedPattern > 1;
        setNegated(matchedPattern == 1 || matchedPattern == 3);
        return true;
    }

}

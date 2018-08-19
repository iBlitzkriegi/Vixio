package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class CondHasPermission extends Condition {

    static {
        Vixio.getInstance().registerCondition(CondHasPermission.class,
                "%members% (has|have) [the] discord perm[ission][s] %permissions%",
                "%users% (has|have) [the] discord perm[ission][s] %permissions% [in %guild%]",
                "%members% (don't|do not|doesn't|does not) have [the] discord perm[ission][s] %permissions%",
                "%users% (don't|do not|doesn't|does not) have [the] discord perm[ission][s] %permissions% [in %guild%]")
                .setName("Member Has Permission")
                .setDesc("Check if a member has a permission.")
                .setExample("if event-member has permission voice connect");
    }

    private Expression<Member> members;
    private Expression<Permission> permissions;

    @Override
    public boolean check(Event e) {
        Permission[] perms = permissions.getArray(e);

        if (perms == null || perms.length == 0)
            return false;

        return EasyMultiple.check(members.getArray(e), member -> member.hasPermission(perms), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return members.toString(e, debug) + (isNegated() ? " don't have " : " have ") + "the discord permission " + permissions.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (matchedPattern == 1 || matchedPattern == 3)
            members = SkriptUtil.combineUserAndGuild((Expression<User>) exprs[0], (Expression<Guild>) exprs[2]);
        else
            members = (Expression<Member>) exprs[0];

        permissions = (Expression<Permission>) exprs[1];
        setNegated(matchedPattern > 1);
        return true;
    }

}

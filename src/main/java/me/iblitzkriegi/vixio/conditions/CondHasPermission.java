package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class CondHasPermission extends Condition {

	static {
		Vixio.getInstance().registerCondition(CondHasPermission.class,
				"%member% has permission %permission%", "%user% has permission %permission% [in %guild%]",
				"%member% doesn't have permission %permission%", "%user% doesn't have permission %permission% [in %guild%]"
		);
	}

	private Expression<Member> member;
	private Expression<Permission> permisson;
	@Override

	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (matchedPattern == 1 || matchedPattern == 3) {
			member = SkriptUtil.combineUserAndGuild((Expression<User>) exprs[0], (Expression<Guild>) exprs[2]);
		} else {
			member = (Expression<Member>) exprs[0];
		}
		permisson = (Expression<Permission>) exprs[1];
		setNegated(matchedPattern > 1);
		return true;
	}

	@Override
	public boolean check(Event e) {
		Member member = this.member.getSingle(e);
		Permission permission = this.permisson.getSingle(e);
		if (permission == null || member == null) {
			return false;
		}
		return isNegated() != member.hasPermission(permission);
	}

	@Override
	public String toString(Event e, boolean debug) {
		return member.toString(e, debug) + " has permission " + permisson.toString(e, debug);
	}

}

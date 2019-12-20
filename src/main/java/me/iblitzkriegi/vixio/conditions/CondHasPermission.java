package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.event.Event;

public class CondHasPermission extends Condition {

    static {
        Vixio.getInstance().registerCondition(CondHasPermission.class,
                "%member% has permission %permission% [in %-channel%]", "%user% has permission %permission% [in %guild/channel%]",
                "%member% (doesn[']t|does not) have permission %permission% [in %-channel%]", "%user% (doesn[']t|does not) have permission %permission% [in %guild/channel%]")
                .setName("Member Has Permission")
                .setDesc("Check if a member has a permission, can also check if they have a permission in a certain GuildChannel.")
                .setExample("if event-member has permission voice connect");
    }

    private Expression<User> user;
    private Expression<Member> member;
    private Expression<Permission> permission;
    private Expression<Object> object;

    @Override

    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (matchedPattern == 1 || matchedPattern == 3) {
            user = (Expression<User>) exprs[0];
        } else {
            member = (Expression<Member>) exprs[0];
        }
        permission = (Expression<Permission>) exprs[1];
        object = (Expression<Object>) exprs[2];
        setNegated(matchedPattern > 1);
        return true;
    }

    @Override
    public boolean check(Event e) {
        Member member = this.member == null ? null : this.member.getSingle(e);
        User user = this.user == null ? null : this.user.getSingle(e);
        Object object = this.object == null ? null : this.object.getSingle(e);
        Permission permission = this.permission.getSingle(e);
        if (permission == null) {
            return false;
        }
        if (member != null) {
            if (object == null) {
                return isNegated() != member.hasPermission(permission);
            }
            if (object instanceof GuildChannel) {
                return isNegated() != member.hasPermission((GuildChannel) object, permission);
            }
            return false;
        }

        if (user != null && object != null) {
            Guild guild = null;
            if (object instanceof Guild) {
                guild = (Guild) object;
            } else if (object instanceof GuildChannel) {
                guild = ((GuildChannel) object).getGuild();
            }
            if (guild == null) {
                return false;
            }
            member = guild.getMember(user);
            if (member == null) {
                return false;
            }

            if (object instanceof GuildChannel) {
                return isNegated() != member.hasPermission((GuildChannel) object, permission);
            } else if (object instanceof Guild) {
                return isNegated() != member.hasPermission(permission);
            }

            return false;

        }

        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (member == null ? user.toString(e, debug) : member.toString(e, debug) + " has permission " + permission.toString(e, debug) + (object == null ? "" : " in " + object.toString(e, debug)));
    }

}

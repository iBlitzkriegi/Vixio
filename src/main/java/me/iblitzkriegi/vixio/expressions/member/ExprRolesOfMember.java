package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeablePropertyExpression;
import me.iblitzkriegi.vixio.util.Convertable;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

public class ExprRolesOfMember extends ChangeablePropertyExpression<Member, Role> implements Convertable<Member, Role> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprRolesOfMember.class, Role.class,
                "role", "members")
                .setName("Roles of Member")
                .setDesc("Get the roles that a Member has in a Guild. You can remove, add and set the roles.")
                .setExample("remove role with id \"6110981981981\" from roles of event-member");
    }


    @Override
    protected Role[] get(Event e, Member[] members) {
        return convert(getReturnType(), getExpr().getAll(e), m -> m.getRoles().toArray(new Role[m.getRoles().size()]));
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "roles of " + getExpr().toString(e, debug);
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode, boolean vixioChanger) {
        if ((mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD) && getExpr().isSingle()) {
            return new Class[]{Role[].class};
        }
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Member>) exprs[0]);
        return true;
    }

    @Override
    public void change(final Event e, final Object[] delta, Bot bot, final Changer.ChangeMode mode) {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("modify roles of member");
            return;
        }
        boolean isAdd = mode == Changer.ChangeMode.ADD;
        Role role = (Role) delta[0];

/*        Member member = (e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild bindedGuild = Util.bindGuild(bot, role.getGuild());
        if (member == null || bot == null || bindedGuild == null) {
            return;
        }

        try {
            if (delta.length == 1) {
                if (isAdd) {
                    bindedGuild.getController().addSingleRoleToMember(member, (Role) delta[0]).queue();
                    return;
                }
                bindedGuild.getController().removeSingleRoleFromMember(member, (Role) delta[0]).queue();
                return;
            }

            ArrayList<Role> roles = new ArrayList<>();
            for (Object rolez : delta) {
                roles.add((Role) rolez);
            }
            if (isAdd) {
                bindedGuild.getController().addRolesToMember(member, roles).queue();
                return;
            }
            bindedGuild.getController().removeRolesFromMember(member, roles).queue();

        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "modify role");
        }*/
    }

}

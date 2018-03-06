package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimpleExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.managers.GuildController;
import org.bukkit.event.Event;

public class ExprRolesOfMember extends ChangeableSimpleExpression<Role> implements EasyMultiple<Member, Role> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprRolesOfMember.class, Role.class,
                "role", "members")
                .setName("Roles of Member")
                .setDesc("Get the roles that a Member has in a Guild. You can remove, add and set the roles.")
                .setExample("remove role with id \"6110981981981\" from roles of event-member");
    }

    private Expression<Member> members;

    @Override
    protected Role[] get(Event e) {
        return convert(getReturnType(), members.getAll(e), m -> m.getRoles().toArray(new Role[m.getRoles().size()]));
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "roles of " + members.toString(e, debug);
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.RESET
                || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.SET
                || mode == Changer.ChangeMode.REMOVE_ALL) {
            return new Class[]{Role[].class};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        members = (Expression<Member>) exprs[0];
        return true;
    }

    @Override
    public void change(final Event e, final Object[] delta, Bot bot, final Changer.ChangeMode mode) {
        change(members.getAll(e), member -> {
            Guild guild = Util.bindGuild(bot, member.getGuild());
            if (guild == null) {
                return;
            }
            GuildController controller = guild.getController();
            try {
                switch (mode) {
                    case RESET:
                    case DELETE:
                    case SET:
                        controller.removeRolesFromMember(member, member.getRoles()).queue();
                        if (mode == Changer.ChangeMode.SET) {
                            controller.addRolesToMember(member, Util.convertedArray(Role.class, delta)).queue();
                        }
                        break;
                    case ADD:
                        controller.addRolesToMember(member, Util.convertedArray(Role.class, delta)).queue();
                        break;
                    case REMOVE:
                    case REMOVE_ALL:
                        controller.removeRolesFromMember(member, Util.convertedArray(Role.class, delta)).queue();
                }
            } catch (PermissionException e1) {
                Vixio.getErrorHandler().warn("Vixio encountered a permission exception while trying to change roles");
            }
        });
    }
}

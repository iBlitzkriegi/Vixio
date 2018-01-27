package me.iblitzkriegi.vixio.expressions.guild.controller;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExprRolesOfMember extends SimpleExpression<Role> {
    static {
        Vixio.getInstance().registerExpression(ExprRolesOfMember.class, Role.class, ExpressionType.SIMPLE,
                "role[s] of %members% [(with|as) %-bot/string%]")
                .setName("Roles of Member")
                .setDesc("Get the roles that a Member has in a Guild. Changers: REMOVE, ADD")
                .setExample("remove role with id \"6110981981981\" from roles of event-member");
    }

    private Expression<Member> member;
    private Expression<Object> bot;

    @Override
    protected Role[] get(Event e) {
        if (member.getAll(e) == null) {
            return null;
        }

        List<Role> roles = new ArrayList<>();
        Arrays.stream(member.getAll(e))
                .filter(Objects::nonNull)
                .forEach(member -> roles.addAll(member.getRoles()));

        return roles.toArray(new Role[roles.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Role> getReturnType() {
        return Role.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "roles of " + member.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        member = (Expression<Member>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD) && member.isSingle()) {
            return new Class[]{Role[].class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("modify roles of member");
            return;
        }
        boolean isAdd = mode == Changer.ChangeMode.ADD;
        Role role = (Role) delta[0];
        Member member = this.member.getSingle(e);
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
        }
    }
}

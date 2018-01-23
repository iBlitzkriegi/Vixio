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
                "role[s] of %members% [as %bot/string%]")
                .setName("Roles of Member")
                .setDesc("Get the roles that a Member has in a Guild")
                .setExample("Coming Soon!");
    }
    Expression<Member> member;
    Expression<Object> bot;
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
        boolean isAdd = mode == Changer.ChangeMode.ADD ? true : false;
        Role role = (Role) delta[0];
        Object object = this.bot.getSingle(e);
        if (object == null) {
            return;
        }
        Member member = this.member.getSingle(e);
        if (member == null) {
            return;
        }
        Guild guild = role.getGuild();
        Bot bot = Util.botFrom(object);
        if (bot == null) {
            Vixio.getErrorHandler().cantFindBot(this.bot.toString(), "add role");
            return;
        }
        try {
            if (delta.length == 1) {
                if (Util.botIsConnected(bot, guild.getJDA())) {
                    if (isAdd) {
                        guild.getController().addSingleRoleToMember(member, (Role) delta[0]).queue();
                        return;
                    }
                    guild.getController().removeSingleRoleFromMember(member, (Role) delta[0]).queue();
                    return;
                }
                if (isAdd) {
                    bot.getJDA().getGuildById(guild.getId()).getController().addSingleRoleToMember(member, (Role) delta[0]).queue();
                    return;
                }
                bot.getJDA().getGuildById(guild.getId()).getController().removeSingleRoleFromMember(member, (Role) delta[0]).queue();
                return;
            }
            ArrayList<Role> roles = new ArrayList<>();
            for (int i = 0; i < delta.length; i++) {
                roles.add((Role) delta[i]);
            }
            if (Util.botIsConnected(bot, guild.getJDA())) {
                if (isAdd) {
                    guild.getController().addRolesToMember(member, roles).queue();
                    return;
                }
                guild.getController().removeRolesFromMember(member, roles).queue();
                return;
            }
            if (isAdd) {
                bot.getJDA().getGuildById(guild.getId()).getController().addRolesToMember(member, roles).queue();
                return;
            }
            bot.getJDA().getGuildById(guild.getId()).getController().removeRolesFromMember(member, roles).queue();
            return;

        }catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "modify role");
        }
    }
}

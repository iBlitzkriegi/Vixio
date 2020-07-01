package me.iblitzkriegi.vixio.events.member;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;

public class EvtAddRole extends BaseEvent<GuildMemberRoleAddEvent> {
    static {

        BaseEvent.register("member add role", EvtAddRole.class, RoleAddEvent.class,
                "member role add[ed]")
                .setName("Role Added")
                .setDesc("Fired when a Member receives a new role or roles. You can use the `the roles` expression to get all the roles.")
                .setExample("on member role add:");

        EventValues.registerEventValue(RoleAddEvent.class, Bot.class, new Getter<Bot, RoleAddEvent>() {
            @Override
            public Bot get(RoleAddEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(RoleAddEvent.class, Guild.class, new Getter<Guild, RoleAddEvent>() {
            @Override
            public Guild get(RoleAddEvent event) {
                return event.getJDAEvent().getGuild();
            }

        }, 0);
        EventValues.registerEventValue(RoleAddEvent.class, Role.class, new Getter<Role, RoleAddEvent>() {
            @Override
            public Role get(RoleAddEvent event) {
                return event.getJDAEvent().getRoles().get(0);
            }
        }, 0);

        EventValues.registerEventValue(RoleAddEvent.class, User.class, new Getter<User, RoleAddEvent>() {
            @Override
            public User get(RoleAddEvent event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(RoleAddEvent.class, Member.class, new Getter<Member, RoleAddEvent>() {
            @Override
            public Member get(RoleAddEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

    }

    public class RoleAddEvent extends SimpleVixioEvent<GuildMemberRoleAddEvent> {
    }

}

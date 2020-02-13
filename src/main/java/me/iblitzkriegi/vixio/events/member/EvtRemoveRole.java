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
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;

public class EvtRemoveRole extends BaseEvent<GuildMemberRoleRemoveEvent> {

    static {

        BaseEvent.register("member role remove", EvtRemoveRole.class, RoleRemoveEvent.class,
                "member role remove[d]")
                .setName("Role Removed")
                .setDesc("Fired when a role is removed from a member.")
                .setExample("on member role remove:");

        EventValues.registerEventValue(RoleRemoveEvent.class, Bot.class, new Getter<Bot, RoleRemoveEvent>() {
            @Override
            public Bot get(RoleRemoveEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(RoleRemoveEvent.class, User.class, new Getter<User, RoleRemoveEvent>() {
            @Override
            public User get(RoleRemoveEvent event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(RoleRemoveEvent.class, Member.class, new Getter<Member, RoleRemoveEvent>() {
            @Override
            public Member get(RoleRemoveEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(RoleRemoveEvent.class, Guild.class, new Getter<Guild, RoleRemoveEvent>() {
            @Override
            public Guild get(RoleRemoveEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(RoleRemoveEvent.class, Role.class, new Getter<Role, RoleRemoveEvent>() {
            @Override
            public Role get(RoleRemoveEvent event) {
                return event.getJDAEvent().getRoles().get(0);
            }
        }, 0);
    }

    public static class RoleRemoveEvent extends SimpleVixioEvent<GuildMemberRoleRemoveEvent> {
    }

}

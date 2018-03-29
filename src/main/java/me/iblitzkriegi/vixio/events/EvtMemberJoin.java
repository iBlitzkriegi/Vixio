package me.iblitzkriegi.vixio.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;

public class EvtMemberJoin extends BaseEvent<GuildMemberJoinEvent> {
    static {
        BaseEvent.register("member join", EvtMemberJoin.class, MemberJoinEvent.class, "member join")
                .setName("Member join")
                .setDesc("Fired when a new member joins a guild")
                .setExample("on member join seen by \"Jewel\"");


        EventValues.registerEventValue(MemberJoinEvent.class, Bot.class, new Getter<Bot, MemberJoinEvent>() {
            @Override
            public Bot get(MemberJoinEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(MemberJoinEvent.class, Member.class, new Getter<Member, MemberJoinEvent>() {
            @Override
            public Member get(MemberJoinEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(MemberJoinEvent.class, Guild.class, new Getter<Guild, MemberJoinEvent>() {
            @Override
            public Guild get(MemberJoinEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(MemberJoinEvent.class, User.class, new Getter<User, MemberJoinEvent>() {
            @Override
            public User get(MemberJoinEvent event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);
    }

    public class MemberJoinEvent extends SimpleVixioEvent<GuildMemberJoinEvent> {

    }

}

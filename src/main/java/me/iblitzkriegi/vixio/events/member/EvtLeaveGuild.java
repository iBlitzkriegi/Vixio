package me.iblitzkriegi.vixio.events.member;

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
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;

public class EvtLeaveGuild extends BaseEvent<GuildMemberLeaveEvent> {

    static {

        BaseEvent.register("user join guild", EvtLeaveGuild.class, JoinGuildEvent.class,
                "(guild|member) leave (guild|server)")
                .setName("Guild Leave")
                .setDesc("Fired when a user leaves a guild. Could be caused by kicking them or them leaving on their own free will.")
                .setExample("on guild join:");

        EventValues.registerEventValue(JoinGuildEvent.class, Bot.class, new Getter<Bot, JoinGuildEvent>() {
            @Override
            public Bot get(JoinGuildEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(JoinGuildEvent.class, User.class, new Getter<User, JoinGuildEvent>() {
            @Override
            public User get(JoinGuildEvent event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(JoinGuildEvent.class, Member.class, new Getter<Member, JoinGuildEvent>() {
            @Override
            public Member get(JoinGuildEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(JoinGuildEvent.class, Guild.class, new Getter<Guild, JoinGuildEvent>() {
            @Override
            public Guild get(JoinGuildEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

    }

    public class JoinGuildEvent extends SimpleVixioEvent<GuildMemberLeaveEvent> {
    }

}

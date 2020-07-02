package me.iblitzkriegi.vixio.events.member;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public class EvtLeaveGuild extends BaseEvent<GuildMemberRemoveEvent> {

    static {

        BaseEvent.register("user join guild", EvtLeaveGuild.class, JoinGuildEvent.class,
                "(guild|member) leave (guild|server)")
                .setName("Guild Leave")
                .setDesc("Fired when a user leaves a guild. Could be caused by kicking them or them leaving on their own free will.")
                .setExample("on member leave guild:");

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

    public class JoinGuildEvent extends SimpleVixioEvent<GuildMemberRemoveEvent> {
    }

}

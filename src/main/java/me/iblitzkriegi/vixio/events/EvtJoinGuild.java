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

public class EvtJoinGuild extends BaseEvent<GuildMemberJoinEvent, EvtJoinGuild.JoinGuildEvent> {

    static {

        BaseEvent.register("user join guild", EvtJoinGuild.class, JoinGuildEvent.class,
                "(guild|member) join guild");

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

    @Override
    public Class<GuildMemberJoinEvent> getJDAClass() {
        return GuildMemberJoinEvent.class;
    }

    @Override
    public Class<JoinGuildEvent> getBukkitClass() {
        return JoinGuildEvent.class;
    }

    public class JoinGuildEvent extends SimpleVixioEvent<GuildMemberJoinEvent> {
    }

}

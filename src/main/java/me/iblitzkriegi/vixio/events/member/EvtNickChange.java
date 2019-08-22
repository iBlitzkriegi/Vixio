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
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;


public class EvtNickChange extends BaseEvent<GuildMemberUpdateNicknameEvent> {
    static {

        BaseEvent.register("member nickname change", EvtNickChange.class, MemberNickChange.class, "[member] nick[name] (change|update)")
                .setName("Nickname Change")
                .setDesc("Fired when a member changes their nickname.")
                .setExample("on nickname update:");

        EventValues.registerEventValue(MemberNickChange.class, Bot.class, new Getter<Bot, MemberNickChange>() {
            @Override
            public Bot get(MemberNickChange event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(MemberNickChange.class, User.class, new Getter<User, MemberNickChange>() {
            @Override
            public User get(MemberNickChange event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(MemberNickChange.class, Member.class, new Getter<Member, MemberNickChange>() {
            @Override
            public Member get(MemberNickChange event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(MemberNickChange.class, Guild.class, new Getter<Guild, MemberNickChange>() {
            @Override
            public Guild get(MemberNickChange event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);


    }

    public class MemberNickChange extends SimpleVixioEvent<GuildMemberUpdateNicknameEvent> {

    }

}

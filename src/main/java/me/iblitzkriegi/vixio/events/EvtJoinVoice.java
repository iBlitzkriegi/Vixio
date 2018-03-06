package me.iblitzkriegi.vixio.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;

public class EvtJoinVoice extends BaseEvent<GuildVoiceJoinEvent> {

    //TODO: add event values
    static {
        BaseEvent.register("join voice", EvtJoinVoice.class, JoinVoiceEvent.class,
                "user join voice", "voice [channel] join")
                .setName("Voice Join")
                .setDesc("Fired when a user joins a voice channel")
                .setExample("on voice channel join:");

        EventValues.registerEventValue(JoinVoiceEvent.class, Bot.class, new Getter<Bot, JoinVoiceEvent>() {
            @Override
            public Bot get(JoinVoiceEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(JoinVoiceEvent.class, User.class, new Getter<User, JoinVoiceEvent>() {
            @Override
            public User get(JoinVoiceEvent event) {
                return event.getJDAEvent().getMember().getUser();
            }
        }, 0);

        EventValues.registerEventValue(JoinVoiceEvent.class, Member.class, new Getter<Member, JoinVoiceEvent>() {
            @Override
            public Member get(JoinVoiceEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

    }

    public class JoinVoiceEvent extends SimpleVixioEvent<GuildVoiceJoinEvent> {
    }

}

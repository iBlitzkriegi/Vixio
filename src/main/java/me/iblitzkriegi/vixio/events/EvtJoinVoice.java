package me.iblitzkriegi.vixio.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;

public class EvtJoinVoice extends BaseEvent<GuildVoiceJoinEvent> {

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

        EventValues.registerEventValue(JoinVoiceEvent.class, VoiceChannel.class, new Getter<VoiceChannel, JoinVoiceEvent>() {
            @Override
            public VoiceChannel get(JoinVoiceEvent event) {
                return event.getJDAEvent().getChannelJoined();
            }
        }, 0);

        EventValues.registerEventValue(JoinVoiceEvent.class, GuildChannel.class, new Getter<GuildChannel, JoinVoiceEvent>() {
            @Override
            public GuildChannel get(JoinVoiceEvent event) {
                return event.getJDAEvent().getChannelJoined();
            }
        }, 0);

        EventValues.registerEventValue(JoinVoiceEvent.class, Guild.class, new Getter<Guild, JoinVoiceEvent>() {
            @Override
            public Guild get(JoinVoiceEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);


    }

    public class JoinVoiceEvent extends SimpleVixioEvent<GuildVoiceJoinEvent> {
    }

}

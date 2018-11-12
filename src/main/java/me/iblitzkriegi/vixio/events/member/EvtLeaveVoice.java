package me.iblitzkriegi.vixio.events.member;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;

public class EvtLeaveVoice extends BaseEvent<GuildVoiceLeaveEvent> {

    static {
        BaseEvent.register("leave voice", EvtLeaveVoice.class, LeaveVoiceEvent.class,
                "user leave voice", "voice [channel] leave")
                .setName("Voice Leave")
                .setDesc("Fired when a user leaves a voice channel")
                .setExample("on voice channel leave:");

        EventValues.registerEventValue(LeaveVoiceEvent.class, Bot.class, new Getter<Bot, LeaveVoiceEvent>() {
            @Override
            public Bot get(LeaveVoiceEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(LeaveVoiceEvent.class, User.class, new Getter<User, LeaveVoiceEvent>() {
            @Override
            public User get(LeaveVoiceEvent event) {
                return event.getJDAEvent().getMember().getUser();
            }
        }, 0);

        EventValues.registerEventValue(LeaveVoiceEvent.class, Member.class, new Getter<Member, LeaveVoiceEvent>() {
            @Override
            public Member get(LeaveVoiceEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(LeaveVoiceEvent.class, VoiceChannel.class, new Getter<VoiceChannel, LeaveVoiceEvent>() {
            @Override
            public VoiceChannel get(LeaveVoiceEvent event) {
                return event.getJDAEvent().getChannelLeft();
            }
        }, 0);

        EventValues.registerEventValue(LeaveVoiceEvent.class, Channel.class, new Getter<Channel, LeaveVoiceEvent>() {
            @Override
            public Channel get(LeaveVoiceEvent event) {
                return event.getJDAEvent().getChannelLeft();
            }
        }, 0);

        EventValues.registerEventValue(LeaveVoiceEvent.class, Guild.class, new Getter<Guild, LeaveVoiceEvent>() {
            @Override
            public Guild get(LeaveVoiceEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);


    }

    public class LeaveVoiceEvent extends SimpleVixioEvent<GuildVoiceLeaveEvent> {
    }

}
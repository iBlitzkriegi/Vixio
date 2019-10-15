package me.iblitzkriegi.vixio.events.member;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;

public class EvtChannelSwitch extends BaseEvent<GuildVoiceMoveEvent> {

    static {
        BaseEvent.register("member switch voice channel", EvtChannelSwitch.class, MoveVoiceEvent.class,
                "member (switch|move) [voice] channel")
                .setName("Member Switch Voice Channel")
                .setDesc("Fired when a member switches voice channels.")
                .setExample(
                        "on member switch voice channel:",
                        "\tbroadcast \"%event-user% left %old channel% and joined %event-channel%\""
                );

        EventValues.registerEventValue(MoveVoiceEvent.class, Bot.class, new Getter<Bot, MoveVoiceEvent>() {
            @Override
            public Bot get(MoveVoiceEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(MoveVoiceEvent.class, Member.class, new Getter<Member, MoveVoiceEvent>() {
            @Override
            public Member get(MoveVoiceEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(MoveVoiceEvent.class, GuildChannel.class, new Getter<GuildChannel, MoveVoiceEvent>() {
            @Override
            public GuildChannel get(MoveVoiceEvent event) {
                return event.getJDAEvent().getChannelJoined();
            }
        }, 0);

        EventValues.registerEventValue(MoveVoiceEvent.class, Guild.class, new Getter<Guild, MoveVoiceEvent>() {
            @Override
            public Guild get(MoveVoiceEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(MoveVoiceEvent.class, User.class, new Getter<User, MoveVoiceEvent>() {
            @Override
            public User get(MoveVoiceEvent event) {
                return event.getJDAEvent().getMember().getUser();
            }
        }, 0);



    }

    public class MoveVoiceEvent extends SimpleVixioEvent<GuildVoiceMoveEvent> {}

}

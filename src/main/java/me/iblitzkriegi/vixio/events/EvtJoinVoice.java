package me.iblitzkriegi.vixio.events;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleBukkitEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import org.bukkit.event.Event;

public class EvtJoinVoice extends BaseEvent<GuildVoiceJoinEvent, EvtJoinVoice.JoinVoiceEvent> {

    //TODO: add event values
    static {
        BaseEvent.register("join voice", EvtJoinVoice.class, JoinVoiceEvent.class,
                "user join voice");

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

    @Override
    public Class<GuildVoiceJoinEvent> getJDAClass() {
        return GuildVoiceJoinEvent.class;
    }

    @Override
    public Class<JoinVoiceEvent> getBukkitClass() {
        return JoinVoiceEvent.class;
    }

    public class JoinVoiceEvent extends SimpleVixioEvent<GuildVoiceJoinEvent> {
    }

}

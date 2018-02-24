package me.iblitzkriegi.vixio.events;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleEvent;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleBukkitEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import org.bukkit.event.Event;

public class EvtJoinVoice extends BaseEvent<GuildVoiceJoinEvent, EvtJoinVoice.JoinVoiceEvent> {

    static {
        BaseEvent.register("join voice", EvtJoinVoice.class, JoinVoiceEvent.class,
                "user join voice");
    }

    @Override
    public JoinVoiceEvent getNewEvent(GuildVoiceJoinEvent JDAEvent) {
        return new JoinVoiceEvent();
    }

    @Override
    public Class<GuildVoiceJoinEvent> getJDAClass() {
        return GuildVoiceJoinEvent.class;
    }

    @Override
    public Class<JoinVoiceEvent> getBukkitClass() {
        return JoinVoiceEvent.class;
    }

    public class JoinVoiceEvent extends SimpleBukkitEvent {
    }

}

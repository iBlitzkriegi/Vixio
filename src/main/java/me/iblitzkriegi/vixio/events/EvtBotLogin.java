package me.iblitzkriegi.vixio.events;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.events.ReadyEvent;

public class EvtBotLogin extends BaseEvent<ReadyEvent> {

    static {
        BaseEvent.register("bot connect", EvtBotLogin.class, BotLoginEvent.class,
                "[vixio] bot (login|connect)")
                .setName("Bot Login Event")
                .setDesc("Fired when a Vixio bot first logs in")
                .setExample(
                        "on bot connect:",
                        "\tbroadcast \"%event-bot% is ready to go!\""
                );

        EventValues.registerEventValue(BotLoginEvent.class, Bot.class, new Getter<Bot, BotLoginEvent>() {
            @Override
            public Bot get(BotLoginEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);


    }

    public class BotLoginEvent extends SimpleVixioEvent<ReadyEvent> {
    }

}

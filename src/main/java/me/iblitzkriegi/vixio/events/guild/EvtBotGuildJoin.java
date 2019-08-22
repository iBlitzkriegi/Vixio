package me.iblitzkriegi.vixio.events.guild;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;

public class EvtBotGuildJoin extends BaseEvent<GuildJoinEvent> {
    static {
        BaseEvent.register("bot join guild", EvtBotGuildJoin.class, BotGuildJoin.class, "bot (join|enter) [new] guild")
                .setName("Bot Join Guild")
                .setDesc("Fired when a bot joins a new guild.")
                .setExample("on bot join guild");

        EventValues.registerEventValue(BotGuildJoin.class, Bot.class, new Getter<Bot, BotGuildJoin>() {
            @Override
            public Bot get(BotGuildJoin event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(BotGuildJoin.class, Guild.class, new Getter<Guild, BotGuildJoin>() {
            @Override
            public Guild get(BotGuildJoin event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);
    }

    public class BotGuildJoin extends SimpleVixioEvent<GuildJoinEvent> {

    }
}

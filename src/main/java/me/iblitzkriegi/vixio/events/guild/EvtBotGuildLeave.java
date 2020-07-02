package me.iblitzkriegi.vixio.events.guild;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public class EvtBotGuildLeave extends BaseEvent<GuildMemberRemoveEvent> {
    static {
        BaseEvent.register("bot leave guild", EvtBotGuildLeave.class, BotLeaveGuild.class, "bot (leave|exit) guild")
                .setName("Bot Leave Guild")
                .setDesc("Fired when a bot leaves a guild. This could be caused by getting kicked or just naturally leaving.")
                .setExample("on bot leave guild:");

        EventValues.registerEventValue(BotLeaveGuild.class, Bot.class, new Getter<Bot, BotLeaveGuild>() {
            @Override
            public Bot get(BotLeaveGuild event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(BotLeaveGuild.class, Guild.class, new Getter<Guild, BotLeaveGuild>() {
            @Override
            public Guild get(BotLeaveGuild event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

    }

    public class BotLeaveGuild extends SimpleVixioEvent<GuildMemberRemoveEvent> {}
}

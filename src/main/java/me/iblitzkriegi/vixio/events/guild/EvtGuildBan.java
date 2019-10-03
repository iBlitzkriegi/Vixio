package me.iblitzkriegi.vixio.events.guild;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;

public class EvtGuildBan extends BaseEvent<GuildBanEvent> {
    static {
        BaseEvent.register("user banned", EvtGuildBan.class, GuildBan.class, "user ban[ned]")
                .setName("User Banned")
                .setDesc("Fired when a user is banned from a guild.")
                .setExample("on user banned:");

        EventValues.registerEventValue(GuildBan.class, Bot.class, new Getter<Bot, GuildBan>() {
            @Override
            public Bot get(GuildBan event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(GuildBan.class, User.class, new Getter<User, GuildBan>() {
            @Override
            public User get(GuildBan event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(GuildBan.class, Guild.class, new Getter<Guild, GuildBan>() {
            @Override
            public Guild get(GuildBan event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);


    }

    public class GuildBan extends SimpleVixioEvent<GuildBanEvent> {
    }
}

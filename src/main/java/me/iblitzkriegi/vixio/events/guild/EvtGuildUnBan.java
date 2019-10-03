package me.iblitzkriegi.vixio.events.guild;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;

public class EvtGuildUnBan extends BaseEvent<GuildUnbanEvent> {
    static {
        BaseEvent.register("user unbanned", EvtGuildUnBan.class, GuildUnban.class, "user unban[ned]")
                .setName("User Unbanned")
                .setDesc("Fired when a user is unbanned from a guild.")
                .setExample("on user unbanned:");

        EventValues.registerEventValue(GuildUnban.class, Bot.class, new Getter<Bot, GuildUnban>() {
            @Override
            public Bot get(GuildUnban event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(GuildUnban.class, User.class, new Getter<User, GuildUnban>() {
            @Override
            public User get(GuildUnban event) {
                return event.getJDAEvent().getUser();
            }
        }, 0);

        EventValues.registerEventValue(GuildUnban.class, Guild.class, new Getter<Guild, GuildUnban>() {
            @Override
            public Guild get(GuildUnban event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);


    }

    public class GuildUnban extends SimpleVixioEvent<GuildUnbanEvent> {
    }
}

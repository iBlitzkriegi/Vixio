package me.iblitzkriegi.vixio.events.message;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

public class EvtMessageEdit extends BaseEvent<GuildMessageUpdateEvent> {

    static {
        BaseEvent.register("guild message edited",
                EvtMessageEdit.class, GuildMessageEditEvent.class, "(guild|server) message edit[ed]")
                .setName("Guild Message Edited")
                .setDesc("Fired when a message is edited in a text channel that the bot can read.")
                .setExample("on guild message edited seen by \"a bot\":");
        EventValues.registerEventValue(GuildMessageEditEvent.class, GuildChannel.class, new Getter<GuildChannel, GuildMessageEditEvent>() {
            @Override
            public GuildChannel get(GuildMessageEditEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, MessageChannel.class, new Getter<MessageChannel, GuildMessageEditEvent>() {
            @Override
            public MessageChannel get(GuildMessageEditEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, User.class, new Getter<User, GuildMessageEditEvent>() {
            @Override
            public User get(GuildMessageEditEvent event) {
                return event.getJDAEvent().getAuthor();
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, Member.class, new Getter<Member, GuildMessageEditEvent>() {
            @Override
            public Member get(GuildMessageEditEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, UpdatingMessage.class, new Getter<UpdatingMessage, GuildMessageEditEvent>() {
            @Override
            public UpdatingMessage get(GuildMessageEditEvent event) {
                return UpdatingMessage.from(event.getJDAEvent().getMessage());
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, Guild.class, new Getter<Guild, GuildMessageEditEvent>() {
            @Override
            public Guild get(GuildMessageEditEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, Bot.class, new Getter<Bot, GuildMessageEditEvent>() {
            @Override
            public Bot get(GuildMessageEditEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);

        EventValues.registerEventValue(GuildMessageEditEvent.class, String.class, new Getter<String, GuildMessageEditEvent>() {
            @Override
            public String get(GuildMessageEditEvent event) {
                return event.getJDAEvent().getMessage().getContentRaw();
            }
        }, 0);

    }

    @Override
    public boolean init(Literal<?>[] exprs, int matchedPattern, SkriptParser.ParseResult parser) {
        return super.init(exprs, matchedPattern, parser);
    }

    @Override
    public boolean check(GuildMessageUpdateEvent e) {
        return true;
    }

    public class GuildMessageEditEvent extends SimpleVixioEvent<GuildMessageUpdateEvent> {
    }

}

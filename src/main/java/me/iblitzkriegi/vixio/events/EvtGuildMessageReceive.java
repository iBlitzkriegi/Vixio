package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class EvtGuildMessageReceive extends BaseEvent<GuildMessageReceivedEvent> {

    static {
        BaseEvent.register("guild message received", "<receive(d)?( seen)?|sent> [by %-string%]",
                EvtGuildMessageReceive.class, GuildMessageReceiveEvent.class, "(guild|server) message")
                .setName("Guild Message Received")
                .setDesc("Fired when a message is sent in a text channel that the bot can read.")
                .setExample("on guild message received seen by \"a bot\":");
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, Channel.class, new Getter<Channel, GuildMessageReceiveEvent>() {
            @Override
            public Channel get(GuildMessageReceiveEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, MessageChannel.class, new Getter<MessageChannel, GuildMessageReceiveEvent>() {
            @Override
            public MessageChannel get(GuildMessageReceiveEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, User.class, new Getter<User, GuildMessageReceiveEvent>() {
            @Override
            public User get(GuildMessageReceiveEvent event) {
                return event.getJDAEvent().getAuthor();
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, Member.class, new Getter<Member, GuildMessageReceiveEvent>() {
            @Override
            public Member get(GuildMessageReceiveEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, UpdatingMessage.class, new Getter<UpdatingMessage, GuildMessageReceiveEvent>() {
            @Override
            public UpdatingMessage get(GuildMessageReceiveEvent event) {
                return UpdatingMessage.from(event.getJDAEvent().getMessage());
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, Guild.class, new Getter<Guild, GuildMessageReceiveEvent>() {
            @Override
            public Guild get(GuildMessageReceiveEvent event) {
                return event.getJDAEvent().getGuild();
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, Bot.class, new Getter<Bot, GuildMessageReceiveEvent>() {
            @Override
            public Bot get(GuildMessageReceiveEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);
        EventValues.registerEventValue(GuildMessageReceiveEvent.class, String.class, new Getter<String, GuildMessageReceiveEvent>() {
            @Override
            public String get(GuildMessageReceiveEvent event) {
                return event.getJDAEvent().getMessage().getContentRaw();
            }
        }, 0);

    }

    private boolean sent;

    @Override
    public boolean init(Literal<?>[] exprs, int matchedPattern, SkriptParser.ParseResult parser) {
        sent = parser.regexes.get(0).group().equals("sent");
        return super.init(exprs, matchedPattern, parser);
    }

    @Override
    public boolean check(GuildMessageReceivedEvent e) {
        if (sent && Util.botFromID(e.getAuthor().getId()) != null) { // if the mode is sent and the author is one of our bots
            return super.check(e);
        } else if (!sent && Util.botFromID(e.getAuthor().getId()) == null) { // if the mode is receive and the author isn't one of our bots
            return super.check(e);
        }
        return false;
    }

    public class GuildMessageReceiveEvent extends SimpleVixioEvent<GuildMessageReceivedEvent> {
    }

}

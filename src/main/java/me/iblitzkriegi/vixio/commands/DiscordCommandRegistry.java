package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.config.Config;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiscordCommandRegistry extends SelfRegisteringSkriptEvent {

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandRegistry.class, DiscordCommandEvent.class, "discord command <([^\\s]+)( .+)?$>")
                .setName("Discord Command")
                .setDesc("Vixio's custom Discord command system")
                .setExample(
                        "discord command cmd <member>:",
                        "\tprefixes: \"hey \", \"%mention tag of event-bot% \", ##",
                        "\taliases: info, user",
                        "\troles: Dev",
                        "\tdescription: Get some information about a user",
                        "\tusage: hey info <member>",
                        "\tbots: {@bot}",
                        "\texecutable in: guild",
                        "\ttrigger:",
                        "\t\tset {_} to a message builder",
                        "\t\tappend line \"-=Who is %name of arg-1%=-\"",
                        "\t\tappend line \"Name: %name of arg-1%\"",
                        "\t\tappend line \"ID: %id of arg-1%\"",
                        "\t\tappend line \"Mention tag: %mention tag of arg-1%\"",
                        "\t\tappend line \"Status: %online status of arg-1%\"",
                        "\t\treply with {_}"
                );

        EventValues.registerEventValue(DiscordCommandEvent.class, DiscordCommand.class, new Getter<DiscordCommand, DiscordCommandEvent>() {
                    @Override
                    public DiscordCommand get(DiscordCommandEvent event) {
                        return event.getCommand();
                    }
                }
                , 0);

        EventValues.registerEventValue(DiscordCommandEvent.class, User.class, new Getter<User, DiscordCommandEvent>() {
                    @Override
                    public User get(DiscordCommandEvent event) {
                        return event.getUser();
                    }
                }
                , 0);


        EventValues.registerEventValue(DiscordCommandEvent.class, Member.class, new Getter<Member, DiscordCommandEvent>() {
                    @Override
                    public Member get(DiscordCommandEvent event) {
                        return event.getMember();
                    }
                }
                , 0);

        EventValues.registerEventValue(DiscordCommandEvent.class, Channel.class, new Getter<Channel, DiscordCommandEvent>() {
                    @Override
                    public Channel get(DiscordCommandEvent event) {
                        return event.getChannel();
                    }
                }
                , 0);


        EventValues.registerEventValue(DiscordCommandEvent.class, MessageChannel.class, new Getter<MessageChannel, DiscordCommandEvent>() {
                    @Override
                    public MessageChannel get(DiscordCommandEvent event) {
                        return event.getMessageChannel();
                    }
                }
                , 0);

        EventValues.registerEventValue(DiscordCommandEvent.class, UpdatingMessage.class, new Getter<UpdatingMessage, DiscordCommandEvent>() {
                    @Override
                    public UpdatingMessage get(DiscordCommandEvent event) {
                        return UpdatingMessage.from(event.getMessage());
                    }
                }
                , 0);

        EventValues.registerEventValue(DiscordCommandEvent.class, Guild.class, new Getter<Guild, DiscordCommandEvent>() {
                    @Override
                    public Guild get(DiscordCommandEvent event) {
                        return event.getGuild();
                    }
                }
                , 0);

        EventValues.registerEventValue(DiscordCommandEvent.class, Bot.class, new Getter<Bot, DiscordCommandEvent>() {
            @Override
            public Bot get(DiscordCommandEvent event) {
                return event.getBot();
            }
        }, 0);
    }

    private String arguments;
    private String command;

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
        command = parser.regexes.get(0).group(1);
        arguments = parser.regexes.get(0).group(2);
        SectionNode sectionNode = (SectionNode) SkriptLogger.getNode();

        String originalName = ScriptLoader.getCurrentEventName();
        Class<? extends Event>[] originalEvents = ScriptLoader.getCurrentEvents();
        Kleenean originalDelay = ScriptLoader.hasDelayBefore;
        ScriptLoader.setCurrentEvent("discord command", DiscordCommandEvent.class);

        DiscordCommand cmd = DiscordCommandFactory.getInstance().add(sectionNode);

        ScriptLoader.setCurrentEvent(originalName, originalEvents);
        ScriptLoader.hasDelayBefore = originalDelay;

        nukeSectionNode(sectionNode);

        return cmd != null;
    }

    @Override
    public void afterParse(Config config) {
    }

    @Override
    public void register(Trigger t) {
    }

    @Override
    public void unregister(Trigger t) {
        DiscordCommandFactory.getInstance().remove(command);
    }

    @Override
    public void unregisterAll() {
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "discord command " + command + (arguments == null ? "" : arguments);
    }

    public void nukeSectionNode(SectionNode sectionNode) {
        List<Node> nodes = new ArrayList<>();
        for (Iterator<Node> iterator = sectionNode.iterator(); iterator.hasNext(); ) {
            nodes.add(iterator.next());
        }
        for (Node n : nodes) {
            sectionNode.remove(n);
        }
    }

}
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
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiscordCommandRegistry extends SelfRegisteringSkriptEvent {

    static {
        Vixio.getInstance().registerEvent("Discord Command", DiscordCommandRegistry.class, DiscordCommandEvent.class, "discord command <([^\\s]+)( .+)?$>");

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

        EventValues.registerEventValue(DiscordCommandEvent.class, MessageChannel.class, new Getter<MessageChannel, DiscordCommandEvent>() {
                    @Override
                    public MessageChannel get(DiscordCommandEvent event) {
                        return event.getChannel();
                    }
                }
                , 0);

        EventValues.registerEventValue(DiscordCommandEvent.class, Message.class, new Getter<Message, DiscordCommandEvent>() {
                    @Override
                    public Message get(DiscordCommandEvent event) {
                        return event.getMessage();
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
    public void register(final Trigger t) {
    }

    @Override
    public void unregister(final Trigger t) {
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
        for (Iterator<Node> iterator = sectionNode.iterator(); iterator.hasNext(); )
            nodes.add(iterator.next());
        for (Node n : nodes)
            sectionNode.remove(n);
    }

}
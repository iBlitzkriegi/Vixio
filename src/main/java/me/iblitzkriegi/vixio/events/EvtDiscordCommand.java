package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.commands.DiscordCommandEvent;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class EvtDiscordCommand extends SkriptEvent {

    static {

        Vixio.getInstance().registerEvent("discord command event", EvtDiscordCommand.class, VixioCommandEvent.class,
                "vixio command [%-string%]");

        /*
        * TODO: come back to these event values when you figure out why they break the DiscordCommandEvent event values
        * just by being registered
        */

        /*
        EventValues.registerEventValue(VixioCommandEvent.class, DiscordCommand.class, new Getter<DiscordCommand, VixioCommandEvent>() {
                    @Override
                    public DiscordCommand get(VixioCommandEvent event) {
                        return event.getCommand();
                    }
                }
                , 0);

        EventValues.registerEventValue(VixioCommandEvent.class, User.class, new Getter<User, VixioCommandEvent>() {
                    @Override
                    public User get(VixioCommandEvent event) {
                        return event.getUser();
                    }
                }
                , 0);


        EventValues.registerEventValue(VixioCommandEvent.class, Member.class, new Getter<Member, VixioCommandEvent>() {
                    @Override
                    public Member get(VixioCommandEvent event) {
                        return event.getMember();
                    }
                }
                , 0);

        EventValues.registerEventValue(VixioCommandEvent.class, Channel.class, new Getter<Channel, VixioCommandEvent>() {
                    @Override
                    public Channel get(VixioCommandEvent event) {
                        return event.getChannel();
                    }
                }
                , 0);


        EventValues.registerEventValue(VixioCommandEvent.class, MessageChannel.class, new Getter<MessageChannel, VixioCommandEvent>() {
                    @Override
                    public MessageChannel get(VixioCommandEvent event) {
                        return event.getMessageChannel();
                    }
                }
                , 0);

        EventValues.registerEventValue(VixioCommandEvent.class, Message.class, new Getter<Message, VixioCommandEvent>() {
                    @Override
                    public Message get(VixioCommandEvent event) {
                        return event.getMessage();
                    }
                }
                , 0);

        EventValues.registerEventValue(VixioCommandEvent.class, Guild.class, new Getter<Guild, VixioCommandEvent>() {
                    @Override
                    public Guild get(VixioCommandEvent event) {
                        return event.getGuild();
                    }
                }
                , 0);

        EventValues.registerEventValue(VixioCommandEvent.class, Bot.class, new Getter<Bot, VixioCommandEvent>() {
            @Override
            public Bot get(VixioCommandEvent event) {
                return event.getBot();
            }
        }, 0);
        */

    }

    private String command;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        command = args[0] == null ? null : ((Literal<String>) args[0]).getSingle();
        return true;
    }

    @Override
    public boolean check(Event e) {
        return command == null || command.equals(((VixioCommandEvent) e).getCommand().getName());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "discord command" + (command == null ? "" : " " + command);
    }

    public static class VixioCommandEvent extends DiscordCommandEvent implements Cancellable {

        private boolean cancelled = false;

        public VixioCommandEvent(String prefix, String usedAlias, DiscordCommand command, Guild guild,
                                 MessageChannel messagechannel, Channel channel, Message message, User user,
                                 Member member, Bot bot) {
            super(prefix, usedAlias, command, guild, messagechannel, channel, message, user, member, bot);
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }

    }

}

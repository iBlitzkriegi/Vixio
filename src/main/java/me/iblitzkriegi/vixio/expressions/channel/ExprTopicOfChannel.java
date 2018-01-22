package me.iblitzkriegi.vixio.expressions.channel;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Blitz on 8/10/2017.
 */
public class ExprTopicOfChannel extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprTopicOfChannel.class, String.class, ExpressionType.SIMPLE, "topic of %channels/channelbuilders% [(with|as) %bot/string%]")
                .setName("Topic of Channel")
                .setDesc("Get/Reset/Set the topic of a channel. Must include a bot to modify the topic!")
                .setExample("set topic of event-channel as event-bot to \"Hi Pika\"");
    }

    private Expression<Object> channel;
    private Expression<Object> bot;

    @Override
    protected String[] get(Event event) {
        Object[] channels = this.channel.getAll(event);
        if (channels == null) {
            return null;
        }
        List<String> topics = new ArrayList<>();
        for (Object object : channels) {
            if (object instanceof ChannelBuilder) {
                topics.add(((ChannelBuilder) object).getTopic());
            } else if (object instanceof Channel && ((Channel) object).getType() == ChannelType.TEXT) {
                TextChannel channel = (TextChannel) object;
                topics.add(channel.getTopic());
            }
        }
        return topics.toArray(new String[topics.size()]);
    }

    @Override
    public boolean isSingle() {
        return channel.isSingle();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "topic of " + channel.toString(event, b) + (bot == null ? "" : " as " + bot.toString(event, b));
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        channel = (Expression<Object>) expressions[0];
        bot = (Expression<Object>) expressions[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET)
            return new Class[]{String.class};
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        String topic = mode == Changer.ChangeMode.SET ? (String) delta[0] : null;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }
        Object[] channels = this.channel.getAll(e);
        if (channels == null) {
            return;
        }
        for (Object object : channels) {
            if (object instanceof ChannelBuilder) {
                ((ChannelBuilder) object).setTopic(topic);
            } else if (object instanceof Channel) {
                if (((Channel) object).getType().equals(ChannelType.TEXT)) {
                    try {
                        TextChannel textChannel = (TextChannel) object;
                        if (Util.botIsConnected(bot, textChannel.getJDA())) {
                            textChannel.getManager().setTopic(topic).queue();
                        } else {
                            TextChannel bindingChannel = bot.getJDA().getTextChannelById(textChannel.getId());
                            if (bindingChannel != null) {
                                bindingChannel.getManager().setTopic(topic).queue();
                            }
                        }
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, "set topic", x.getPermission().getName());
                    }
                }
            }
        }
    }

}

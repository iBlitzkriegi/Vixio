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
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * Created by Blitz on 8/10/2017.
 */
public class ExprTopicOfChannel extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprTopicOfChannel.class, String.class, ExpressionType.SIMPLE, "topic of %channels% [(with|as) %bot/string%]")
                .setName("Topic of Channel")
                .setDesc("Get/Reset/Set the topic of a channel. Must include a bot to modify the topic!")
                .setExample("set topic of event-channel as event-bot to \"Hi Pika\"");
    }

    private Expression<Channel> channel;
    private Expression<Object> bot;

    @Override
    protected String[] get(Event event) {
        ArrayList<String> channels = new ArrayList<>();
        for (Channel channel : this.channel.getAll(event)) {
            if (channel.getType() == ChannelType.TEXT) {
                channels.add(((TextChannel) channel).getTopic());
            }
        }
        return channels.toArray(new String[channels.size()]);
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
        channel = (Expression<Channel>) expressions[0];
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
        Object object = bot.getSingle(e);
        if (object == null) {
            Skript.error("You must include a bot in order to modify the topic!");
            return;
        }
        Bot bot = Util.botFrom(object);
        if (bot == null) {
            Skript.error("Could not parse provided bot! Either input a %bot% type or the name you give to your bot on login.");
            return;
        }
        if (channel.getAll(e) == null) {
            Skript.error("You must input at least one channel to get the topic of!");
            return;
        }
        try {
            for (Channel channel : channel.getAll(e)) {
                if (channel.getType() == ChannelType.TEXT) {
                    if (Util.botIsConnected(bot, channel.getJDA())) {
                        channel.getManager().setTopic(topic).queue();
                    } else {
                        try {
                            bot.getJDA().getTextChannelById(channel.getId()).getManager().setTopic(topic).queue();
                        } catch (NullPointerException x) {

                        }
                    }
                }
            }
        } catch (PermissionException x) {
            Skript.error("Provided bot could not find one of the provided channels.");
        }
    }

}

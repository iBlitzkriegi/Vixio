package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.SelfUser;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;


/**
 * Created by Blitz on 8/10/2017.
 */
public class ExprTopicOfChannel extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprTopicOfChannel.class, String.class, ExpressionType.SIMPLE, "topic of %channel% [(with|as) %-bot%]")
                .setName("Topic of Channel")
                .setDesc("Get/Reset/Set the topic of a channel. Must include a bot to modify the topic!")
                .setExample("set topic of event-channel as event-bot to \"Hi Pika\"");
    }

    private Expression<TextChannel> channel;
    private Expression<Bot> bot;

    @Override
    protected String[] get(Event event) {
        TextChannel channel = this.channel.getSingle(event);
        if (channel == null) return null;

        return new String[]{channel.getTopic()};
    }

    @Override
    public boolean isSingle() {
        return true;
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
        channel = (Expression<TextChannel>) expressions[0];
        bot = (Expression<Bot>) expressions[1];
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
        if (bot.getSingle(e) != null) {
            JDA jda = bot.getSingle(e).getJDA();
            if(jda != null) {
                if (channel.getSingle(e) != null) {
                    TextChannel channel = jda.getTextChannelById(this.channel.getSingle(e).getId());
                    try {
                        switch (mode) {
                            case RESET:
                            case DELETE:
                                channel.getManager().setTopic(null).queue();
                                break;
                            case SET:
                                channel.getManager().setTopic((String) delta[0]).queue();
                        }
                    } catch (PermissionException x) {
                        Skript.error("Provided bot does not have enough permission to modify the topic of the provided channel");
                    }
                }else{
                    Skript.error("Provided bot could not find provided channel.");
                }
            }else{
                Skript.error("Could not find stored bot by the stored bot.");
            }
        } else {
            Skript.error("You must include a bot in order to modify the topic!");

        }
    }

}

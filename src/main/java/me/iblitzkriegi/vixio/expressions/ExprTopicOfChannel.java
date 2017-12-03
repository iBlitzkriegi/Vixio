package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
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
    private Expression<SelfUser> bot;

    @Override
    protected String[] get(Event event) {
        if(channel.getSingle(event).getTopic()!=null) {
            return new String[]{channel.getSingle(event).getTopic()};
        }else{
            return null;
        }
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
        return "topic of channel";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        channel = (Expression<TextChannel>) expressions[0];
        if(expressions[1]!=null) {
            bot = (Expression<SelfUser>) expressions[1];
        }else{
            bot = null;
        }
        return true;
    }
    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET)
            return new Class[] {String.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        if(bot!=null) {
            if (Vixio.getInstance().jdaUsers.get(bot.getSingle(e)) != null) {
                JDA jda = Vixio.getInstance().jdaUsers.get(bot.getSingle(e));
                TextChannel channel = jda.getTextChannelById(this.channel.getSingle(e).getId());
                try {
                    switch (mode) {
                        case SET:
                            String topic;
                            if (delta[0] != null) {
                                topic = String.valueOf(delta[0]);
                            } else {
                                Skript.error("You must include something to set the topic to");
                                return;
                            }
                            channel.getManager().setTopic(topic).queue();
                            break;
                        case DELETE:
                            channel.getManager().setTopic(null).queue();
                            break;
                        case RESET:
                            channel.getManager().setTopic(null).queue();
                            break;
                    }
                }catch (PermissionException x){
                    Skript.error("Provided bot does not have enough permission to modify the topic of the provided channel");
                }
            }else{
                Skript.error("Could not find stored bot");
            }
        }else{
            Skript.error("You must include a bot in order to modify the topic!");

        }
    }
}

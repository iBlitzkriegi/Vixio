package me.iblitzkriegi.vixio.expressions.eventValues;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.DiscordEventCompare;
import net.dv8tion.jda.core.events.channel.text.update.TextChannelUpdateTopicEvent;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/19/2017.
 */
public class ExprTheLastTopic extends SimpleExpression<String> {
    public static String oldTopic;
    public static String newTopic;
    private boolean not;
    static {
        Vixio.registerExpression(ExprTheLastTopic.class, String.class, ExpressionType.SIMPLE, "[the] old topic", "[the] new topic");
    }
    @Override
    protected String[] get(Event event) {
        return new String[]{getTopic()};
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
        return not == true ? "the old topic" : "the new topic";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(DiscordEventCompare.getEvent()!=null){
            if(DiscordEventCompare.getEvent().equals(TextChannelUpdateTopicEvent.class)){
                not = i == 0;
                return true;
            }else{
                String s = Vixio.getPattern(TextChannelUpdateTopicEvent.class);
                Skript.error("You may not use topic outside syntax outside of " + s + " events!");
                return false;
            }
        }
        String s = Vixio.getPattern(TextChannelUpdateTopicEvent.class);
        Skript.error("You may not use topic outside syntax outside of the " + s + " event!");
        return false;

    }
    public String getTopic() {
        if(newTopic!=null && oldTopic!=null){
            return not == true ? oldTopic : newTopic;
        }else{
            Skript.error("The event has not fired yet, so why are you trying to get values...?");
            return null;
        }
    }
}

package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.SelfUser;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/16/2017.
 */
public class ExprMessageAs extends SimpleExpression<Message> {
    static {
        Vixio.getInstance().registerExpression(ExprMessageAs.class, Message.class, ExpressionType.SIMPLE, "[message] %message% (as|with) %bot%")
                .setName("Message as bot")
                .setDesc("Used to delete messages")
                .setExample("delete event-message as event-bot");

    }
    private Expression<Message> message;
    private Expression<Bot> bot;
    @Override
    protected Message[] get(Event event) {
        if(message.getSingle(event)!=null) {
            return new Message[]{message.getSingle(event)};
        }
        Skript.error("You must include a message");
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return message.getSingle(event) + " as " + bot.getSingle(event);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expressions[0];
        bot = (Expression<Bot>) expressions[1];
        return true;
    }
    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.DELETE)
            return new Class[] {String.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        if(bot!=null){
            if(message!=null){
                if(Vixio.getInstance().botHashMap.get(bot.getSingle(e))!=null){
                    try{
                        switch (mode){
                            case DELETE:
                                message.getSingle(e).delete().queue();
                                break;
                        }
                    }catch (PermissionException x){
                        Skript.error("Provided bot does not have enough permission to modify the topic of the provided channel");
                    }
                }else{
                    Skript.error("Could not find stored bot");
                }
            }else{
                Skript.error("You must include a message!");
            }
        }else{
            Skript.error("You must include a bot in order to modify the topic!");

        }
    }
}

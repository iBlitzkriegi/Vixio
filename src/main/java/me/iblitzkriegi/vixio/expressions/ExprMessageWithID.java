package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Blitz on 12/22/2016.
 */
@ExprAnnotation.Expression(returntype = Message.class, type = ExpressionType.SIMPLE, syntax = "message with id %string% in textchannel %string%")
public class ExprMessageWithID extends SimpleExpression<Message> {
    Expression<String> vID;
    Expression<String> vChannel;
    @Override
    protected Message[] get(Event e) {
        try {
            return new Message[]{getMessage(e)};
        } catch (RateLimitedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
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
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vID = (Expression<String>) expressions[0];
        vChannel = (Expression<String>) expressions[1];
        return true;
    }
    private Message getMessage(Event e) throws RateLimitedException, ExecutionException, InterruptedException {

        try {
            for (Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()) {
                if(jda.getValue().getTextChannelById(vChannel.getSingle(e))!=null){
                    if(jda.getValue().getTextChannelById(vChannel.getSingle(e)).getMessageById(vID.getSingle(e)).complete()!=null){
                        return jda.getValue().getTextChannelById(vChannel.getSingle(e)).getMessageById(vID.getSingle(e)).complete();
                    }else{
                        Skript.warning("May not reference messages outside of the specified textchannel");
                    }
                }
            }
        }catch (ErrorResponseException x){
            Skript.warning(x.getLocalizedMessage());
        }
        return null;
    }
}

package me.iblitzkriegi.vixio.expressions.guildInfo;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.RestAction;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/19/2016.
 */
@ExprAnnotation.Expression(returntype = RestAction.class, type = ExpressionType.SIMPLE, syntax = "[discord] pinned messages in channel %string%")
public class ExprPinnedMessages extends SimpleExpression<RestAction> {
    Expression<String> vChannel;
    @Override
    protected RestAction[] get(Event e) {
        return new RestAction[]{pinnedMessages(e)};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends RestAction> getReturnType() {
        return RestAction.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vChannel = (Expression<String>) expr[0];
        return true;
    }
    private RestAction<List<Message>> pinnedMessages(Event e){
        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            TextChannel channel = jda.getValue().getTextChannelById(vChannel.getSingle(e));
            return channel.getPinnedMessages();

        }
        return null;
    }
}

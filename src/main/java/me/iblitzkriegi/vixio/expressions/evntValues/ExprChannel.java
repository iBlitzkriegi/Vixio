package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceive;
import me.iblitzkriegi.vixio.events.EvntUserJoinVc;
import me.iblitzkriegi.vixio.events.EvntUserLeaveVc;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.Channel;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(returntype = Channel.class, type = ExpressionType.SIMPLE, syntax = "[event-]channel")
public class ExprChannel extends SimpleExpression<Channel> {
    @Override
    protected Channel[] get(Event e) {
        return new Channel[]{getChannel(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Channel> getReturnType() {
        return Channel.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntUserJoinVc.class)
                ){
            return true;
        }
        Skript.warning("You may not use Event-Channel outside of Discord events.");
        return false;
    }
    @Nullable
    private static Channel getChannel(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getEvntChannel();
        }else if(e instanceof EvntPrivateMessageReceive){
            ((EvntPrivateMessageReceive)e).getEvntChannel();
        }else if(e instanceof EvntUserJoinVc){
            ((EvntUserJoinVc)e).getEvntChannel();
        }else if(e instanceof EvntUserLeaveVc){
            ((EvntUserLeaveVc)e).getEvntChannel();
        }
        return null;
    }

}

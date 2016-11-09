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
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[event-]string")
public class ExprString extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        return new String[]{getString(e)};
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
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)) {
            return true;
        }else if (ScriptLoader.isCurrentEvent(EvntPrivateMessageReceive.class)) {
            return true;
        }
        Skript.warning("Cannot use 'Message' outside of discord events!");
        return false;
    }
    @Nullable
    private static String getString(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getEvntMessage().getContent();
        }else if(e instanceof EvntPrivateMessageReceive){
            return ((EvntPrivateMessageReceive)e).getEvntMessage().getContent();
        }
        return null;
    }

}

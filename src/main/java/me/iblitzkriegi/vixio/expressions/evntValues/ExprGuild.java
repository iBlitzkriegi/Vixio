package me.iblitzkriegi.vixio.expressions.evntValues;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.*;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(returntype = Guild.class, type = ExpressionType.SIMPLE, syntax = "[event-]guild")
public class ExprGuild extends SimpleExpression<Guild> {
    @Override
    protected Guild[] get(Event e) {
        return new Guild[]{getGuild(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(ScriptLoader.isCurrentEvent(EvntGuildMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMemberJoin.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMemberLeave.class)
                | ScriptLoader.isCurrentEvent(EvntUserJoinVc.class)
                ){
            return true;
        }
        Skript.warning("You may not use event-guild outside of Discord events.");
        return false;
    }
    @Nullable
    private static Guild getGuild(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getGuild();
        }else if (e instanceof EvntGuildMemberJoin) {
            return ((EvntGuildMemberJoin) e).getEvntGuild();
        }else if (e instanceof EvntGuildMemberLeave) {
            return ((EvntGuildMemberLeave) e).getEvntGuild();
        }else if (e instanceof EvntUserJoinVc) {
            return ((EvntUserJoinVc) e).getEvntGuild();
        }else if (e instanceof EvntUserLeaveVc) {
            return ((EvntUserLeaveVc) e).getEvntGuild();
        }
        return null;
    }

}

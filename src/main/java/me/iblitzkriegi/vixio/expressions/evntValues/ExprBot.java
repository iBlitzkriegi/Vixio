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
import net.dv8tion.jda.core.entities.User;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/1/2016.
 */
@ExprAnnotation.Expression(
        name = "eventbot",
        title = "event-bot",
        desc = "Get the Bot out of any Vixio event",
        syntax = "[event-]bot",
        returntype = User.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprBot extends SimpleExpression<User> {
    @Override
    protected User[] get(Event e) {
        return new User[]{getBot(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
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
                | ScriptLoader.isCurrentEvent(EvntPrivateMessageReceive.class)
                | ScriptLoader.isCurrentEvent(EvntUserStatusChange.class)
                | ScriptLoader.isCurrentEvent(EvntUserJoinVc.class)
                | ScriptLoader.isCurrentEvent(EvntTextChannelCreated.class)
                | ScriptLoader.isCurrentEvent(EvntTextChannelDeleted.class)
                | ScriptLoader.isCurrentEvent(EvntGuildBan.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMessageBotSend.class)
                | ScriptLoader.isCurrentEvent(EvntUserStartStreaming.class)
                | ScriptLoader.isCurrentEvent(EvntMessageAddReaction.class)
                | ScriptLoader.isCurrentEvent(EvntAudioPlayerTrackStart.class)
                | ScriptLoader.isCurrentEvent(EvntAudioPlayerTrackEnd.class)
                | ScriptLoader.isCurrentEvent(EvntGuildMessageBotSend.class)
                ){
            return true;
        }
        Skript.warning("You may not use event-bot outside of Discord events.");
        return false;
    }
    @Nullable
    private static User getBot(Event e) {
        if (e == null)
            return null;
        if (e instanceof EvntGuildMessageReceive) {
            return ((EvntGuildMessageReceive) e).getJDA().getSelfUser();
        }else if (e instanceof EvntPrivateMessageReceive) {
            return ((EvntPrivateMessageReceive) e).getJDA().getSelfUser();
        }else if (e instanceof EvntGuildMemberJoin) {
            return ((EvntGuildMemberJoin) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntGuildMemberLeave) {
            return ((EvntGuildMemberLeave) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntUserStatusChange) {
            return ((EvntUserStatusChange) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntUserJoinVc) {
            return ((EvntUserJoinVc) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntUserLeaveVc) {
            return ((EvntUserLeaveVc) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntTextChannelCreated) {
            return ((EvntTextChannelCreated) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntTextChannelDeleted) {
            return ((EvntTextChannelDeleted) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntUserAvatarUpdate) {
            return ((EvntUserAvatarUpdate) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntGuildBan) {
            return ((EvntGuildBan) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntGuildMessageBotSend) {
            return ((EvntGuildMessageBotSend) e).getJDA().getSelfUser();
        }else if (e instanceof EvntUserStartStreaming) {
            return ((EvntUserStartStreaming) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntMessageAddReaction) {
            return ((EvntMessageAddReaction) e).getEvntJDA().getSelfUser();
        }else if (e instanceof EvntAudioPlayerTrackStart) {
            return ((EvntAudioPlayerTrackStart) e).getBot();
        }else if (e instanceof EvntAudioPlayerTrackEnd) {
            return ((EvntAudioPlayerTrackEnd) e).getBot();
        }else if (e instanceof EvntGuildMessageBotSend) {
            return ((EvntGuildMessageBotSend) e).getBot();
        }
        return null;
    }

}

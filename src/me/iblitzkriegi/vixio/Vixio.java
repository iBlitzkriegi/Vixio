package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import me.iblitzkriegi.vixio.conditions.*;
import me.iblitzkriegi.vixio.effects.*;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import me.iblitzkriegi.vixio.expressions.user.*;
import me.iblitzkriegi.vixio.expressions.generalexprs.ExprUsernameOf;
import me.iblitzkriegi.vixio.expressions.mentioned.ExprMentioned;
import me.iblitzkriegi.vixio.expressions.mentioned.ExprMentionedAtNum;
import me.iblitzkriegi.vixio.expressions.roles.ExprMentionTagORole;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Blitz on 10/14/2016.
 */
public class Vixio extends JavaPlugin{
    public void onEnable() {
        Skript.registerAddon(this);
        Skript.registerEffect(EffLogin.class, "[discord ]login to [discord ]user with token %string%");
        Skript.registerEffect(EffLogout.class, "[discord ]logout of [discord ]user");
        Skript.registerEffect(EffSendToChannel.class, "[discord ]send %string% to [discord ]channel with id %string%");
        Skript.registerEffect(EffSendUserMessage.class, "send discord message [of ]%string% to [user ][with id ]%string%");
        Skript.registerEffect(EffReplyToMessage.class, "reply (to|with) [discord ][message ][with ]%string%");
        Skript.registerEffect(EffSendTyping.class, "send typing in [discord ]channel [with id ]%string%");
        Skript.registerEffect(EffSetGame.class, "[discord ]set [discord ]user[s] game to %string%");
        Skript.registerEffect(EffMakeCodeblock.class, "(make|create) codeblock %string% [with lang %-string% ]for [channel ]%string%");
        Skript.registerEffect(EffSetIdle.class, "[discord ]set [discord ]user[s ]idle state [to ]%boolean%");
        Skript.registerEffect(EffDeleteMessage.class, "delete [discord ]message %string%");
        Skript.registerCondition(CondLoggedIn.class, "[discord ]user is logged in");
        Skript.registerCondition(CondNotLoggedIn.class, "[discord ]user (isnt|isn't) logged in");
        Skript.registerCondition(CondBeginsWith.class, "%string% (starts|begins) with %string%");
        Skript.registerCondition(CondIsBot.class, "[discord ]user is a bot[ account]");
        Skript.registerCondition(CondIsNotBot.class, "[discord ]user (isnt|isn't) a bot[ account]");
        Skript.registerCondition(CondContainsMention.class, "message contains mentioned users");
        // Event expressions \\
        Skript.registerExpression(ExprMessage.class, String.class, ExpressionType.SIMPLE, "[event-]message");
        Skript.registerExpression(ExprMentioned.class, String.class, ExpressionType.SIMPLE, "[event-]mentioned");
        Skript.registerExpression(ExprUsernameOf.class, String.class, ExpressionType.SIMPLE, "[discord ]username of [discord ][user ][with id ]%string%");
        Skript.registerExpression(ExprString.class, String.class, ExpressionType.SIMPLE, "[event-]string");
        Skript.registerExpression(ExprChannel.class, String.class, ExpressionType.SIMPLE, "[event-]channel");
        Skript.registerExpression(ExprUser.class, String.class, ExpressionType.SIMPLE, "[event-]user");
        Skript.registerExpression(ExprMentionedAtNum.class, String.class, ExpressionType.SIMPLE, "mentioned user at %number%");
        Skript.registerExpression(ExprIdOfUser.class, String.class, ExpressionType.SIMPLE, "[discord ]id of [user ][with id ]%string%");
        Skript.registerExpression(ExprMentionOfUser.class, String.class, ExpressionType.SIMPLE, "[discord ]mention tag of [user ][with id ]%string%");
        Skript.registerExpression(ExprMentionTagORole.class, String.class, ExpressionType.SIMPLE, "[discord ]mention tag of [discord ]role [with id ]%string%");
        // Guild Member Leave and Join \\
//        Skript.registerExpression(ExprUserWhoLeft.class, String.class, ExpressionType.SIMPLE, "[event-]user");
        // Events \\
        Skript.registerEvent("GuildMsgReceived", SimpleEvent.class, EvntGuildMsgReceived.class, "[discord ]guild message [receive][d]");
        Skript.registerEvent("PrivateMsgReceived", SimpleEvent.class, EvntPrivateMessageReceived.class, "[discord ]private message [receive][d]");
        Skript.registerEvent("GuildMemberLeave", SimpleEvent.class, EvntGuildMemberLeave.class, "[discord ]guild [member ]leave");
        Skript.registerEvent("GuildMemberJoin", SimpleEvent.class, EvntGuildMemberJoin.class, "[discord ]guild [member ]join");


    }

}

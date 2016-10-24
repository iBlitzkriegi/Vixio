package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import me.iblitzkriegi.vixio.conditions.*;
import me.iblitzkriegi.vixio.effects.effChannel.*;
import me.iblitzkriegi.vixio.effects.effGenerals.*;
import me.iblitzkriegi.vixio.effects.effGuild.EffAddRole;
import me.iblitzkriegi.vixio.effects.effGuild.EffRemoveRole;
import me.iblitzkriegi.vixio.effects.effGuild.EffSetGuildName;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import me.iblitzkriegi.vixio.expressions.exprChannel.*;
import me.iblitzkriegi.vixio.expressions.exprGuild.*;
import me.iblitzkriegi.vixio.expressions.exprUserValues.*;
import me.iblitzkriegi.vixio.expressions.exprMessage.*;
import me.iblitzkriegi.vixio.expressions.exprMentioned.ExprMentioned;
import me.iblitzkriegi.vixio.expressions.exprMentioned.ExprMentionedAtNum;
import me.iblitzkriegi.vixio.expressions.exprRoles.ExprMentionTagORole;
import me.iblitzkriegi.vixio.expressions.exprRoles.ExprNameOfRole;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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
        Skript.registerEffect(EffAddRole.class, "discord give user %string% [discord ]role [with id ]%string% [in %-string%]");
        Skript.registerEffect(EffRemoveRole.class, "discord remove user %string% from [discord ]role [with id ]%string% [in %-string%]");
        Skript.registerEffect(EffSetIdle.class, "[discord ]set [discord ]user[s ]idle state [to ]%boolean%");
        Skript.registerEffect(EffDeleteMessage.class, "delete [discord ]message %string%");
        Skript.registerEffect(EffSetGuildName.class, "[discord ]set [discord ]guild %string% name to %string%");
        Skript.registerEffect(EffSetChannelName.class, "[discord ]set [discord ]channel %string% name to %string%");
        Skript.registerEffect(EffSetChannelTopic.class, "[discord ]set [discord ]channel %string% topic to %string%");
        Skript.registerEffect(EffPinMessage.class, "[discord ]pin [discord ]message %string%");
        Skript.registerEffect(EffSetChannelPosition.class, "[discord ]set [discord ]channel %string% position to %integer%");
        Skript.registerEffect(EffSetNickname.class, "[discord ]set nickname of [discord ]user %string% to %string%[ in %-string%]");
        Skript.registerEffect(EffSetAvatar.class, "[discord]set [discord ]user[s] avatar to %string%");
        // Conditions \\
        Skript.registerCondition(CondLoggedIn.class, "[discord ]user is logged in");
        Skript.registerCondition(CondLoggedIn.class, "[discord ]user is logged in");
        Skript.registerCondition(CondNotLoggedIn.class, "[discord ]user (isnt|isn't) logged in");
        Skript.registerCondition(CondBeginsWith.class, "%string% (starts|begins) with %string%");
        Skript.registerCondition(CondIsBot.class, "[discord ]user is a bot[ account]");
        Skript.registerCondition(CondIsNotBot.class, "[discord ]user (isnt|isn't) a bot[ account]");
        Skript.registerCondition(CondContainsMention.class, "message contains mentioned users");
        // Event expressions \\
        Skript.registerExpression(ExprMessage.class, String.class, ExpressionType.SIMPLE, "[event-]message");
        Skript.registerExpression(ExprMentioned.class, String.class, ExpressionType.SIMPLE, "[event-]mentioned");
        Skript.registerExpression(ExprUsernameOf.class, String.class, ExpressionType.SIMPLE, "[discord] username of [discord ][user ][with id ]%string%");
        Skript.registerExpression(ExprUserIsBot.class, Boolean.class, ExpressionType.SIMPLE, "[discord ]user %string% is bot");
        Skript.registerExpression(ExprString.class, String.class, ExpressionType.SIMPLE, "[event-]string");
        Skript.registerExpression(ExprChannel.class, String.class, ExpressionType.SIMPLE, "[event-]channel");
        Skript.registerExpression(ExprUser.class, String.class, ExpressionType.SIMPLE, "[event-]user");
        Skript.registerExpression(ExprMentionedAtNum.class, String.class, ExpressionType.SIMPLE, "mentioned user at %integer%");
        Skript.registerExpression(ExprIdOfUser.class, String.class, ExpressionType.SIMPLE, "[discord] id of [user ][with id ]%string%");
        Skript.registerExpression(ExprCurrentGame.class, String.class, ExpressionType.SIMPLE, "[discord] current game of [user ][with id ]%string%");
        Skript.registerExpression(ExprDiscriminatorOf.class, String.class, ExpressionType.SIMPLE, "[discord] discriminator of [user ][with id ]%string%");
        Skript.registerExpression(ExprStatusOf.class, String.class, ExpressionType.SIMPLE, "[discord] status of [user ][with id ]%string%");
        Skript.registerExpression(ExprMentionOfUser.class, String.class, ExpressionType.SIMPLE, "[discord] mention tag of [user ][with id ]%string%");
        Skript.registerExpression(ExprMentionOfUser.class, String.class, ExpressionType.SIMPLE, "[discord] avatar url of [user ][with id ]%string%");
        Skript.registerExpression(ExprMentionTagORole.class, String.class, ExpressionType.SIMPLE, "[discord] mention tag of [discord ]role [with id ]%string%");
        Skript.registerExpression(ExprNameOfRole.class, String.class, ExpressionType.SIMPLE, "[discord] name of [discord ]role [with id ]%string%");
        Skript.registerExpression(ExprAvatarUrlOf.class, String.class, ExpressionType.SIMPLE, "[discord] avatar url of [discord ]user [with id ]%string%");
        // Guild shit \\
        Skript.registerExpression(ExprGuild.class, String.class, ExpressionType.SIMPLE, "[event-]guild");
        Skript.registerExpression(ExprNameOfGuild.class, String.class, ExpressionType.SIMPLE, "name of [discord ][guild ]%string%");
        Skript.registerExpression(ExprIdOfGuild.class, String.class, ExpressionType.SIMPLE, "id of [discord ]guild %string%");
        Skript.registerExpression(ExprOwnerOfGuild.class, String.class, ExpressionType.SIMPLE, "owner of [discord ][guild ]%string%");
        Skript.registerExpression(ExprSizeOfGuild.class, String.class, ExpressionType.SIMPLE, "size of [discord ][guild ]%string%");
        Skript.registerExpression(ExprRegionOfGuild.class, String.class, ExpressionType.SIMPLE, "region of [discord ][guild ]%string%");
        // Channel shit \\
        Skript.registerExpression(ExprChannelName.class, String.class, ExpressionType.SIMPLE, "[discord] name of [discord ]channel [with id ]%string%");
        Skript.registerExpression(ExprTopicOfChannel.class, String.class, ExpressionType.SIMPLE, "[discord] topic of [discord ]channel [with id ]%string%");
        Skript.registerExpression(ExprPinnedMessages.class, List.class, ExpressionType.SIMPLE, "[discord] pinned messages in [discord ]channel [with id ]%string%");
        Skript.registerExpression(ExprUsersInChannel.class, List.class, ExpressionType.SIMPLE, "[discord] users in [discord ]channel [with id ]%string%");
        Skript.registerExpression(ExprPositionOfChannel.class, String.class, ExpressionType.SIMPLE, "[discord] position of [discord ]channel [with id ]%string%");
        // Events \\
        Skript.registerEvent("GuildMsgReceived", SimpleEvent.class, EvntGuildMsgReceived.class, "[discord] guild message [receive][d]");
        Skript.registerEvent("PrivateMsgReceived", SimpleEvent.class, EvntPrivateMessageReceived.class, "[discord ]private message [receive][d]");
        Skript.registerEvent("GuildMemberLeave", SimpleEvent.class, EvntGuildMemberLeave.class, "[discord] guild [member ]leave");
        Skript.registerEvent("GuildMemberJoin", SimpleEvent.class, EvntGuildMemberJoin.class, "[discord] guild [member ]join");


    }

}

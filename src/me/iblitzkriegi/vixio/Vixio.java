package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.conditions.CondBeginsWith;
import me.iblitzkriegi.vixio.conditions.CondIsBot;
import me.iblitzkriegi.vixio.conditions.CondLoggedIn;
import me.iblitzkriegi.vixio.conditions.CondNotLoggedIn;
import me.iblitzkriegi.vixio.effects.*;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import me.iblitzkriegi.vixio.events.EvntPrivateMessageReceived;
import me.iblitzkriegi.vixio.expressions.*;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Blitz on 10/14/2016.
 */
public class Vixio extends JavaPlugin{
    private static Vixio instance;
    public void onEnable() {
        instance = this;
        Skript.registerAddon(this);
        Skript.registerEffect(EffLogin.class, "[discord ]login to [discord ]user with token %string%");
        Skript.registerEffect(EffLogout.class, "[discord ]logout of [discord ]user");
        Skript.registerEffect(EffReply.class, "[discord ]send %string% to [discord ]channel with id %string%");
        Skript.registerEffect(EffSendUserMessage.class, "send discord message [of ]%string% to [user ][with id ]%string%");
        Skript.registerEffect(EffSendTyping.class, "send typing in [discord ]channel [with id ]%string%");
        Skript.registerEffect(EffSetGame.class, "[discord ]set [discord ]user[s] game to %string%");
        Skript.registerEffect(EffMakeBuild.class, "[discord ]make messagebuilder %string% and send it to %string%");
        Skript.registerEffect(EffSetIdle.class, "[discord ]set [discord ]user[s ]idle state [to ]%boolean%");
        Skript.registerCondition(CondLoggedIn.class, "[discord ]user is logged in");
        Skript.registerCondition(CondNotLoggedIn.class, "[discord ]user (isnt|isn't) logged in");
        Skript.registerCondition(CondBeginsWith.class, "%string% (starts|begins) with %string%");
        Skript.registerCondition(CondIsBot.class, "[discord ]user is a bot[ account]");
        Skript.registerCondition(CondIsBot.class, "[discord ]user (isnt|isn't) a bot[ account]");

        // Guild event expressions \\
        Skript.registerExpression(ExprMessage.class, String.class, ExpressionType.SIMPLE, "[event-]message");
        Skript.registerExpression(ExprChannel.class, String.class, ExpressionType.SIMPLE, "[event-]channel");
        Skript.registerExpression(ExprAuthorAsId.class, String.class, ExpressionType.SIMPLE, "[event-]author-id");
        Skript.registerExpression(ExprAuthorAsMention.class, String.class, ExpressionType.SIMPLE, "[event-]author-mention");
        Skript.registerExpression(ExprAuthorUsername.class, String.class, ExpressionType.SIMPLE, "[event-]author-username");
        // Events \\
        Skript.registerEvent("GuildMsgReceived", SimpleEvent.class, EvntGuildMsgReceived.class, "[discord ]guild message [receive][d]");
        EventValues.registerEventValue(EvntGuildMsgReceived.class, String.class, new Getter<String, EvntGuildMsgReceived>() {
            @Nullable
            @Override
            public String get(EvntGuildMsgReceived e) {
                return e.getEvtMsg();
            }
        }, 0);
        EventValues.registerEventValue(EvntGuildMsgReceived.class, String.class, new Getter<String, EvntGuildMsgReceived>() {
            @Nullable
            @Override
            public String get(EvntGuildMsgReceived e) {
                return e.getEvtChannel();
            }
        }, 0);
        Skript.registerEvent("PrivateMsgReceived", SimpleEvent.class, EvntPrivateMessageReceived.class, "[discord ]private message [receive][d]");
        EventValues.registerEventValue(EvntPrivateMessageReceived.class, String.class, new Getter<String, EvntPrivateMessageReceived>() {
            @Nullable
            @Override
            public String get(EvntPrivateMessageReceived e) {
                return e.getEvntMsg();
            }
        }, 0);

    }

}

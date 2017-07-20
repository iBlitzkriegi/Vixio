package me.iblitzkriegi.vixio.conditions.member;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

/**
 * Created by Blitz on 1/1/2017.
 */
@CondAnnotation.Condition(
        name = "UserOnlineStatusIs",
        title = "User Online Status is",
        desc = "Check if the online status of a user is a certain value",
        syntax = "online[[-]status] of user %user% is %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$guild\\\":\\n" +
                "\\t\\tset {online} to 0\\n" +
                "\\t\\tset {dnd} to 0\\n" +
                "\\t\\tset {offline} to 0\\n" +
                "\\t\\tloop members in guild event-guild:\\n" +
                "\\t\\t\\tif online-status of user loop-value is \\\"ONLINE\\\":\\n" +
                "\\t\\t\\t\\tadd 1 to {online}\\n" +
                "\\t\\t\\telse if online-status of user loop-value is \\\"UNKNOWN\\\":\\n" +
                "\\t\\t\\t\\tadd 1 to {dnd}\\n" +
                "\\t\\t\\telse if online-status of user loop-value is \\\"OFFLINE\\\":\\n" +
                "\\t\\t\\t\\tadd 1 to {offline}\\n" +
                "\\t\\treply with \\\"%{online}% users Online, %{dnd}% don't want to be distrubed, and %{offline}% are sleeping.\\\"")
public class CondUserOnlineStatusIs extends Condition {
    Expression<User> vUser;
    Expression<String> vStatus;
    @Override
    public boolean check(Event e) {
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
            for(Guild guild : jda.getValue().getGuilds()){
                if(guild.getMember(vUser.getSingle(e))!=null){
                    String status = vStatus.getSingle(e);
                    String userstatus = guild.getMember(vUser.getSingle(e)).getOnlineStatus().name();
                    if(status.equalsIgnoreCase("Do Not Disturb")){
                        if(userstatus.equalsIgnoreCase("Unknown")){
                            return true;
                        }
                    }else {
                        if (userstatus.equalsIgnoreCase(status)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<User>) expr[0];
        vStatus = (Expression<String>) expr[1];
        return true;
    }
}

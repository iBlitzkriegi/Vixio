package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

/**
 * Created by Blitz on 1/1/2017.
 */
@CondAnnotation.Condition(syntax = "online[[-]status] of user %user% is %string%")
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

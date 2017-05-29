package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Blitz on 11/19/2016.
 */
@CondAnnotation.Condition(
        name = "UserHasRole",
        title = "User has Role",
        desc = "Check if a User has a specific role",
        syntax = "[discord] user %string% has role %string% in %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$permcheck\\\":\\n" +
                "\\t\\tif user event-user has role \\\"Vixio Fanclub\\\" in event-guild:\\n" +
                "\\t\\t\\tbroadcast \\\"Does\\\"")
public class CondUserHasRole extends Condition {
    Expression<String> vRole;
    Expression<String> vGuild;
    Expression<String> vUser;
    @Override
    public boolean check(Event e) {
        return hasRole(e);
    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<String>) expr[0];
        vRole = (Expression<String>) expr[1];
        vGuild = (Expression<String>) expr[2];
        return true;
    }
    private Boolean hasRole(Event e){
        Member member;
        Guild guild;
        for(Map.Entry<String, JDA> j : EffLogin.bots.entrySet()){
            if(j.getValue().getGuildById(vGuild.getSingle(e))!=null){
                guild = j.getValue().getGuildById(vGuild.getSingle(e));
                if(guild.getMemberById(vUser.getSingle(e))!=null){
                    member = j.getValue().getGuildById(vGuild.getSingle(e)).getMemberById(vUser.getSingle(e));
                    try {
                        for (Role s : guild.getRolesByName(vRole.getSingle(e), true)) {
                            return member.getRoles().contains(s);
                        }
                    }catch (NullPointerException x){
                        Skript.warning("Could not find Role with that name.");
                    }
                }else{
                    Skript.warning("The user specified is not in the requested guild.");
                }
            }else{
                Skript.warning("Could not find guild by that ID.");
            }
        }
        return false;
    }
}

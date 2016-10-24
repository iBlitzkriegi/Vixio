package me.iblitzkriegi.vixio.effects.effGuild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.api.API;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/23/2016.
 */
public class EffRemoveRole extends Effect {
    private Expression<String> user;
    private Expression<String> roleid;
    private Expression<String> sGuild;

    @Override
    protected void execute(Event e) {
        if (e instanceof EvntGuildMsgReceived) {
            try {
                Guild guild = ((EvntGuildMsgReceived) e).getEvntGuild();
                User s = guild.getUserById(user.getSingle(e));
                Role r = null;
                for (Role x : guild.getRoles()) {
                    if (x.getId().equalsIgnoreCase(roleid.getSingle(e))) {
                        r = x;
                    }
                }
                if (r != null) {
                    if (guild.getUsersWithRole(r).contains(s)) {
                        guild.getManager().removeRoleFromUser(s, r).update();
                    } else {
                        Skript.warning("That user does not have that role.");
                    }
                } else {
                    Skript.warning("You dun diddly fucked up famberino paparino. Did u try addn moar luberino before gettin fuked by this error?");
                }

            } catch (PermissionException x) {
                Skript.warning(x.getLocalizedMessage());
            }
        } else {
            try {
                if (sGuild != null) {
                    Guild guild = API.getAPI().getJDA().getGuildById(sGuild.getSingle(e));
                    User s = guild.getUserById(user.getSingle(e));
                    Role r = null;
                    for (Role x : guild.getRoles()) {
                        if (x.getId().equalsIgnoreCase(roleid.getSingle(e))) {
                            r = x;
                        }
                    }
                    if (r != null) {
                        if(guild.getUsersWithRole(r).contains(s)) {
                            guild.getManager().removeRoleFromUser(s, r).update();
                        }else{
                            Skript.warning("That user does not have that role.");
                        }
                    } else {
                        Skript.warning("You dun diddly fucked up famberino paparino. Did u try addn moar luberino before gettin fuked by this error?");
                    }
                }
            } catch (PermissionException x) {
                Skript.warning(x.getLocalizedMessage());
            }
        }
    }


    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expr[2]==null) {
            user = (Expression<String>) expr[0];
            roleid = (Expression<String>) expr[1];
            sGuild = null;
        }else{
            user = (Expression<String>) expr[0];
            roleid = (Expression<String>) expr[1];
            sGuild = (Expression<String>) expr[2];
        }
        return true;
    }
}

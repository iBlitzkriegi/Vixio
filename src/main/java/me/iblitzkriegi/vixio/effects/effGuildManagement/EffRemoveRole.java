package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] (remove|take) role %string% from user %string% in guild %string% with %string%")
public class EffRemoveRole extends Effect {
    Expression<String> vRole;
    Expression<String> vUser;
    Expression<String> vBot;
    Expression<String> vGuild;
    @Override
    protected void execute(Event e) {
        try {
            JDA jda = bots.get(vBot.getSingle(e));
            Guild guild = jda.getGuildById(vGuild.getSingle(e));
            User user = jda.getUserById(vUser.getSingle(e));
            for(Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase(vRole.getSingle(e))){
                    try{
                        Member m = guild.getMember(user);
                        guild.getController().removeRolesFromMember(m, r).queue();
                    }catch (PermissionException x){
                        Skript.warning(x.getLocalizedMessage());
                    }
                }
            }
        }catch (NullPointerException x){
            Skript.warning("One of the values you specified were null. Please check your values and ID's and try again.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vRole = (Expression<String>) expr[0];
        vUser = (Expression<String>) expr[1];
        vGuild = (Expression<String>) expr[2];
        vBot = (Expression<String>) expr[3];
        return true;
    }
}

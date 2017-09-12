package me.iblitzkriegi.vixio.conditions.member;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.vCondShowroom;

/**
 * Created by Blitz on 11/19/2016.
 */

public class CondUserHasRole extends Condition {
    static {
        Documentation docs = Vixio.registerCondition(CondUserHasRole.class, "[discord] user %string% has role [with id] %string%", "[discord] user %string% does not have role %string%")
                .setName("User has Role")
                .setDesc("Check if a user has a specific role by its name ")
                .setExample("Coming soon");
        String t = docs.getName().replaceAll(" ", "");
        vCondShowroom.put(t, docs.getName());
        VixioAnnotationParser.vCondSyntax.put(docs.getName().replaceAll(" ", ""), docs.getSyntaxes()[0]);
        VixioAnnotationParser.vCondExample.put(docs.getName().replaceAll(" ", ""), docs.getExample());
        VixioAnnotationParser.vCondDesc.put(docs.getName().replaceAll(" ", ""), docs.getDesc());
    }
    Expression<String> vRole;
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
        return true;
    }
    private Boolean hasRole(Event e){
        Member member;
        Guild guild;
        for(Map.Entry<String, JDA> j : EffLogin.bots.entrySet()){
            if((j.getValue().getRoleById(vRole.getSingle(e))!=null) || (j.getValue().getUserById(vUser.getSingle(e))!=null)) {
                guild = j.getValue().getRoleById(vRole.getSingle(e)).getGuild();
                User u = j.getValue().getUserById(vUser.getSingle(e));
                if (guild.getMember(u) != null) {

                } else {
                    Skript.warning("The provided user is not in the specific Guild.");
                }
            }else{
                Skript.warning("Could not find anything by either the provided Role ID or the User ID");
            }
        }
        return false;
    }
}

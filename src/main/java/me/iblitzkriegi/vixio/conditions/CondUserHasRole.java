package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/19/2016.
 */
@CondAnnotation.Condition(syntax = "[discord] user %string% has role %string% in %string%")
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
        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            Guild LaGuild = jda.getValue().getGuildById(vGuild.getSingle(e));
            Member user = LaGuild.getMemberById(vUser.getSingle(e));
            String urroles = String.valueOf(user.getRoles());
            if(urroles.contains(vRole.getSingle(e))){
                return true;
            }

        }
        return false;
    }
}

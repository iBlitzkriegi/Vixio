package me.iblitzkriegi.vixio.conditions.member;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.Vixio.registerCondition;
import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.vCondShowroom;

/**
 * Created by Blitz on 11/8/2016.
 */
public class CondIsInVoicechannel extends Condition {
    static {
        Documentation docs = registerCondition(CondIsInVoicechannel.class, "user %string% is in a voice channel in guild %string%", "user %string% is not in a voice channel in guild %string%")
                .setName("User is in voice channel")
                .setDesc("Check if a user is or is not in a voice channel in a Guild")
                .setExample("COMING SOON");
        String t = docs.getName().replaceAll(" ", "");
        vCondShowroom.put(t, docs.getName());
        VixioAnnotationParser.vCondSyntax.put(docs.getName().replaceAll(" ", ""), docs.getSyntaxes()[0]);
        VixioAnnotationParser.vCondExample.put(docs.getName().replaceAll(" ", ""), docs.getExample());
        VixioAnnotationParser.vCondDesc.put(docs.getName().replaceAll(" ", ""), docs.getDesc());
    }
    Expression<String> vID;
    Expression<String> vGuild;
    private Boolean not;
    @Override
    public boolean check(Event e) {
        Guild g;
        User u;
        for(Map.Entry<String, JDA> jda : EffLogin.bots.entrySet()){
            if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){
                g = jda.getValue().getGuildById(vGuild.getSingle(e));
                if(jda.getValue().getUserById(vID.getSingle(e))!=null) {
                    u = jda.getValue().getUserById(vID.getSingle(e));
                    if (g.getMember(u) != null) {
                        Boolean isInVoicechannel = g.getMember(u).getVoiceState().inVoiceChannel();
                        if ((not && isInVoicechannel) || (not && !isInVoicechannel)) {
                            return (not == isInVoicechannel);
                        } else if ((!not && isInVoicechannel) || (!not && !isInVoicechannel)) {
                            return (not == isInVoicechannel);
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
        vID = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        not = i == 0;
        return true;

    }
}

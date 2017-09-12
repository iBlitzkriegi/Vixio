package me.iblitzkriegi.vixio.conditions.bot;

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
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.vCondShowroom;

/**
 * Created by Blitz on 11/8/2016.
 */
public class CondBotIsInVoicechannel extends Condition {
    static{
        Documentation docs = Vixio.registerCondition(CondBotIsInVoicechannel.class, "[discord] bot %string% is in [a] voice channel in guild %string%", "[discord] bot %string% is not in [a] voice channel in guild %string%")
            .setName("Bot is in Voice channel")
            .setDesc("Check if one of your bots is in a voice channel in a specific Guild")
            .setExample("COMING SOON");
        String t = docs.getName().replaceAll(" ", "");
        vCondShowroom.put(t, docs.getName());
        VixioAnnotationParser.vCondSyntax.put(docs.getName().replaceAll(" ", ""), docs.getSyntaxes()[0]);
        VixioAnnotationParser.vCondExample.put(docs.getName().replaceAll(" ", ""), docs.getExample());
        VixioAnnotationParser.vCondDesc.put(docs.getName().replaceAll(" ", ""), docs.getDesc());
    }
    private Expression<String> vBot;
    private Expression<String> vGuild;
    private boolean not;
    @Override
    public boolean check(Event e) {
        JDA jda;
        Guild guild;
        if(EffLogin.bots.get(vBot.getSingle(e))!=null){
            jda = EffLogin.bots.get(vBot.getSingle(e));
            if(jda.getGuildById(vGuild.getSingle(e))!=null){
                guild = jda.getGuildById(vGuild.getSingle(e));
                Boolean t = guild.getMember(jda.getSelfUser()).getVoiceState().inVoiceChannel();
                if((not && t) || (not && !t)){
                    return (not == t);
                }else if((!not && t) || (!not && !t)){
                    return (not == t);
                }
            }else{
                Skript.warning("Could not find Guild by the provided ID.");
            }
        }else{
            Skript.warning("Could not find bot via that name, check your login effect for the name that should be provided.");
        }

        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult pResult) {
        vBot = (Expression<String>) expressions[0];
        vGuild = (Expression<String>) expressions[1];
        not = i == 0;
        return true;
    }
}

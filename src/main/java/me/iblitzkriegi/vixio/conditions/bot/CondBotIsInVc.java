package me.iblitzkriegi.vixio.conditions.bot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sun.org.apache.xpath.internal.operations.Bool;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/8/2016.
 */
@CondAnnotation.Condition(
        name = "BotIsInVc",
        title = "Bot is in Voicechannel",
        desc = "Check if your bot is in a Voice Channel",
        syntax = "[discord] bot %string% [(1¦is|2¦is not)] in [a] voice channel in guild %string%",
        example = "N/A")
public class CondBotIsInVc extends Condition {
    Expression<String> vBot;
    Expression<String> vGuild;
    SkriptParser.ParseResult parseResult;
    @Override
    public boolean check(Event e) {
        JDA jda;
        Guild guild;
        if(EffLogin.bots.get(vBot.getSingle(e))!=null){
            jda = EffLogin.bots.get(vBot.getSingle(e));
            if(jda.getGuildById(vGuild.getSingle(e))!=null){
                guild = jda.getGuildById(vGuild.getSingle(e));
                Boolean t = guild.getMember(jda.getSelfUser()).getVoiceState().inVoiceChannel();
                if(parseResult.mark == 1) {
                    if(t){
                        return true;
                    }else{
                        return false;
                    }
                }else if(parseResult.mark == 2){
                    if(t){
                        return false;
                    }else{
                        return true;
                    }
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
        parseResult = pResult;
        vBot = (Expression<String>) expressions[0];
        vGuild = (Expression<String>) expressions[1];
        return true;
    }
}

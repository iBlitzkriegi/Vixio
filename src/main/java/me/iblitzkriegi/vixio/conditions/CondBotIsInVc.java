package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@CondAnnotation.Condition(syntax = "[discord] user %string% is in a voice channel")
public class CondBotIsInVc extends Condition {
    Expression<String> vBot;
    @Override
    public boolean check(Event e) {
        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            for(VoiceChannel vc : jda.getValue().getVoiceChannels()){
                for(Member user : vc.getMembers()){
                    if(user.getUser().getName().equalsIgnoreCase(vBot.getSingle(e))){
                        return true;
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
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expressions[0];
        return true;
    }
}

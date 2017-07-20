package me.iblitzkriegi.vixio.conditions.member;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@CondAnnotation.Condition(
        name = "UserIsInVc",
        title = "User is in Vc",
        desc = "Check if a user is in a Voicechannel",
        syntax = "%string% is in a voice channel",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$summon\\\":\\n" +
                "\\t\\tif event-user is in a voice channel:\\n" +
                "\\t\\t\\tjoin voice channel voice channel of event-user with \\\"Rawr\\\"\\n" +
                "\\t\\t\\treply with \\\"Successfully joined your voice channel.\\\"\\n" +
                "\\t\\telse:\\n" +
                "\\t\\t\\treply with \\\"You aren't currently in a voice channel silly!\\\"")
public class CondIsInVc extends Condition {
    Expression<String> vID;
    @Override
    public boolean check(Event e) {

        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            for(VoiceChannel s : jda.getValue().getVoiceChannels()){
                for(Member u : s.getMembers()){
                    if(u.getUser().getId().equalsIgnoreCase(vID.getSingle(e))){
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
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vID = (Expression<String>) expr[0];
        return true;
    }
}

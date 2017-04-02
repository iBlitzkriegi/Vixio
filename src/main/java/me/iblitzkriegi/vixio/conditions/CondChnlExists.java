package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import org.bukkit.event.Event;

import java.util.Map;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@CondAnnotation.Condition(
        name = "ChannelExists",
        title = "Channel Exists",
        desc = "Check if a channel exists",
        syntax = "channel named %string% exists in guild %string%",
        example = "on guild message receive seen by \\\"Rawr\\\":\\n" +
                "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
                "\\tset {_command} to {_args::1}\\n" +
                "\\tremove {_args::1} from {_args::*}\\n" +
                "\\tif {_command} starts with \\\"$exist\\\":\\n" +
                "\\t\\tif channel named \\\"general\\\" exists in guild event-guild:\\n" +
                "\\t\\t\\tbroadcast \\\"That channel is der lolz\\\"")
public class CondChnlExists extends Condition {
    Expression<String> vName;
    Expression<String> vGuild;
    @Override
    public boolean check(Event e) {
        for(Map.Entry<String, JDA> jda : bots.entrySet()){
            if(jda.getValue().getGuildById(vGuild.getSingle(e))!=null){
                for(Channel vcs : jda.getValue().getGuildById(vGuild.getSingle(e)).getTextChannels()){
                    if(vcs.getName().equalsIgnoreCase(vName.getSingle(e))){
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
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vName = (Expression<String>) e[0];
        vGuild = (Expression<String>) e[1];
        return true;
    }
}

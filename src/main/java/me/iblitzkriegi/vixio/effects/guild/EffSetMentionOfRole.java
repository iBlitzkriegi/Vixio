package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(
        name = "SetMentionoRole",
        title = "Set Mention State of Role",
        desc = "Set a role to be able to be mentioned, must have permission to do so.",
        syntax = "[discord] set mention state of role [named] %string% in guild [with id] %string% to %boolean% as [bot] %string%",
        example = "SOON")
public class EffSetMentionOfRole extends Effect{
    Expression<String> vRole;
    Expression<String> vGuild;
    Expression<String> vBot;
    Expression<Boolean> vBool;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            for(Role s : jda.getGuildById(vGuild.getSingle(e)).getRoles()){
                if(s.getName().equals(vRole.getSingle(e))){
                    try {
                        s.getManager().setMentionable(vBool.getSingle(e)).queue();
                    }catch (PermissionException x){
                        Skript.warning("Not enough permission to do so!");
                    }
                }
            }
            Skript.warning("Could not find Role with that ID");
        }else{
            Skript.warning("Could not find Guild by that ID.");
        }
    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vRole = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[3];
        vBool = (Expression<Boolean>) expr[2];
        return true;
    }
}

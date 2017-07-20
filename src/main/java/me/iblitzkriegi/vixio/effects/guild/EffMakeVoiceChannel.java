package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/1/2016.
 */
@EffectAnnotation.Effect(
        name = "CreateChannel",
        title = "Create TextChannel",
        desc = "Create a TextChannel in a Guild",
        syntax = "[discord] (make|create) [discord] voice[-]channel [named] %string% in guild [with id] %string% as [bot] %string%",
        example = "SOONLUL")
public class EffMakeVoiceChannel extends Effect {
    private Expression<String> sName;
    private Expression<String> vGuild;
    private Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        createChannel(e);
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sName = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }

    private void createChannel(Event e) {
        JDA jda = null;
        Guild vG = null;
        if(bots.get(vBot.getSingle(e))!=null) {
            jda = bots.get(vBot.getSingle(e));
            if(jda.getGuildById(vGuild.getSingle(e))!=null){
                vG = jda.getGuildById(vGuild.getSingle(e));
                try {
                    vG.getController().createVoiceChannel(sName.getSingle(e)).queue();
                }catch (PermissionException x){
                    Skript.warning("Your bot does not have the required permissions to create a voice channel!");
                }
            }else{
                Skript.warning("Could not find Guild by that ID.");
            }
        }else{
            Skript.warning("Could not find bot with that name, this is the bot you logged in with using the login effect!");
        }

    }

}

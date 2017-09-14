package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(
        name = "SetVoicechannelName",
        title = "Set Voice Channel Name",
        desc = "Set the name of a VoiceChannel in a Guild, must have permission to do so",
        syntax = "[discord] set voice[-]channel [with id] %string% name to %string% with [bot] %string%",
        example = "SOON")
public class EffSetVcName extends Effect{
    Expression<String> vChannel;
    Expression<String> vTopic;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        VoiceChannel vTc = jda.getVoiceChannelById(vChannel.getSingle(e));
        try {
            vTc.getManager().setName(vTopic.getSingle(e)).queue();
        }catch (PermissionException x){
            Skript.warning("You do not have enough permission to do so! Bot " + jda.getSelfUser().getName() + " that being");
        }
    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vChannel = (Expression<String>) expr[0];
        vTopic = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

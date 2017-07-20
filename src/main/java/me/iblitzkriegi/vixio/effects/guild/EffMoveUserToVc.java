package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 7/15/2017.
 */
@EffectAnnotation.Effect(name = "MoveUserToVc",
        title = "Move user to vc",
        desc = "Move a user from one voicechannel to another",
        syntax = "move user [with id] %string% to voice channel with id %string% with [bot] %string%",
        example = "n/a")
public class EffMoveUserToVc extends ch.njol.skript.lang.Effect{
    Expression<String> vUser;
    Expression<String> vC;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda;
        VoiceChannel vc;
        User user;
        if(bots.get(vBot.getSingle(e))!=null){            jda = bots.get(vBot.getSingle(e));
            if(jda.getVoiceChannelById(vC.getSingle(e))!=null){
                vc = jda.getVoiceChannelById(vC.getSingle(e));
                Guild g = vc.getGuild();
                if(jda.getUserById(vUser.getSingle(e))!=null){
                    user = jda.getUserById(vUser.getSingle(e));
                    if(g.getMember(user)!=null){
                        Member m = g.getMember(user);
                        if(m.getVoiceState().inVoiceChannel()){
                            try{
                                g.getController().moveVoiceMember(m, vc).queue();
                            }catch (PermissionException x){
                                Skript.warning("The provided bot lacks to permissions in order to execute this.");
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<String>) expressions[0];
        vC = (Expression<String>) expressions[1];
        vBot = (Expression<String>) expressions[2];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 4/27/2017.
 */
@EffectAnnotation.Effect(
        name = "KickUser",
        title = "Kick User",
        desc = "Kick a user from a Guild",
        syntax = "(kick|remove) user [with id] %string% from guild [with id] %string% with [bot] %string%",
        example = "SUBMIT EXAMPLES TO BLITZ#3273"

)
public class EffKickUser extends Effect {
    private Expression<String> vUser;
    private Expression<String> vGuild;
    private Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        Guild s;
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            s = jda.getGuildById(vGuild.getSingle(e));
            if(s.getMemberById(vUser.getSingle(e))!=null){
                try{
                    s.getController().kick(vUser.getSingle(e)).queue();
                }catch (PermissionException x){
                    Skript.warning("Missing the required permission to kick this user.");
                }
            }else{
                Skript.warning("The specified Member ID is not in the requested Guild.");
            }
        }else{
            Skript.warning("Can not find Guild with that ID.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<String>) expressions[0];
        vGuild = (Expression<String>) expressions[1];
        vBot = (Expression<String>) expressions[2];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 4/27/2017.
 */
@EffectAnnotation.Effect(
        name = "BanUser",
        title = "Ban User",
        desc = "Ban a User from a specific Guild",
        example = "ban user \\\"id\\\" from guild \\\"id\\\" with bot \\\"bot\\\" #To get IDs, go to discord options --> Appearance then check \\\"Developer Mode\\\". You can now right click on channels and users and click \\\"Copy ID\\\" to get their id",
        syntax = "ban user [with id] %string%, purge messages [from last] %number% days, from guild [with id] %string% with [bot] %string%"
)
public class EffBanUser extends Effect {
    Expression<String> vUser;
    Expression<String> vGuild;
    Expression<String> vBot;
    Expression<Number> vDays;
    @Override
    protected void execute(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Guild s;
        if(jda.getGuildById(vGuild.getSingle(e))!=null){
            s = jda.getGuildById(vGuild.getSingle(e));
            if(s.getMemberById(vUser.getSingle(e))!=null){

                try{
                    s.getController().ban(vUser.getSingle(e), Integer.valueOf(String.valueOf(vDays.getSingle(e)))).queue();
                }catch (PermissionException x){
                    Skript.warning("Missing the required permission to kick this user.");
                }
            }else{
                Skript.warning("The specified User is not in the specified Guild.");
            }
        }else{
            Skript.warning("Could not find a Guild with that ID.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<String>) expressions[0];
        vDays = (Expression<Number>) expressions[1];
        vGuild = (Expression<String>) expressions[2];
        vBot = (Expression<String>) expressions[3];
        return true;
    }
}

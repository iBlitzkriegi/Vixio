package me.iblitzkriegi.vixio.effects.effGenerals;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.api.API;
import me.iblitzkriegi.vixio.events.EvntGuildMemberJoin;
import me.iblitzkriegi.vixio.events.EvntGuildMemberLeave;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/23/2016.
 */
public class EffSetNickname extends Effect {
    private Expression<String> sId;
    private Expression<String> sNickname;
    private Expression<String> sGuild;
    @Override
    protected void execute(Event e) {
        if (e instanceof EvntGuildMsgReceived || e instanceof EvntGuildMemberJoin || e instanceof EvntGuildMemberLeave || e instanceof EvntGuildMemberJoin) {
            if(sGuild!=null){
                Guild guild = getAPI().getJDA().getGuildById(sGuild.getSingle(e));
                User user = getAPI().getJDA().getUserById(sId.getSingle(e));
                guild.getManager().setNickname(user, sNickname.getSingle(e));
            }else{
                Guild guild = ((EvntGuildMsgReceived) e).getEvntGuild();
                User user = getAPI().getJDA().getUserById(sId.getSingle(e));
                guild.getManager().setNickname(user, sNickname.getSingle(e));
            }
        }else{
            Guild guild = getAPI().getJDA().getGuildById(sGuild.getSingle(e));
            User user = getAPI().getJDA().getUserById(sId.getSingle(e));
            guild.getManager().setNickname(user, sNickname.getSingle(e));
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expr[2]!=null){
            sId = (Expression<String>) expr[0];
            sNickname = (Expression<String>) expr[1];
            sGuild = (Expression<String>) expr[2];
        }else{
            sId = (Expression<String>) expr[0];
            sNickname = (Expression<String>) expr[1];
            sGuild = null;
        }
        return true;
    }
}

package me.iblitzkriegi.vixio.util;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.events.*;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */

public class MultiBotGuildCompare extends SkriptEvent {
    private Expression<String> sBotName;
    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        sBotName = (Expression<String>) literals[0];
        return true;

    }

    @Override
    public boolean check(Event e) {
        User user = EffLogin.users.get(sBotName.getSingle(e));
        if(e instanceof EvntGuildMessageReceive) {
            User bot = ((EvntGuildMessageReceive) e).getJDA().getSelfUser();
            if (bot.equals(user)) {
                return true;
            }
        }else if(e instanceof EvntPrivateMessageReceive){
            User bot = ((EvntPrivateMessageReceive) e).getJDA().getSelfUser();
            if (bot.equals(user)) {
                return true;
            }
        }else if(e instanceof EvntGuildMemberJoin){
            User bot = ((EvntGuildMemberJoin) e).getEvntJDA().getSelfUser();
            if (bot.equals(user)) {
                return true;
            }
        }else if(e instanceof EvntGuildMemberLeave){
            User bot = ((EvntGuildMemberLeave) e).getEvntJDA().getSelfUser();
            if (bot.equals(user)) {
                return true;
            }
        }else if(e instanceof EvntUserStatusChange){
            User bot = ((EvntUserStatusChange) e).getEvntJDA().getSelfUser();
            if (bot.equals(user)) {
                return true;
            }
        }else if(e instanceof EvntAudioPlayerTrackStart){
            AudioPlayer player = EffLogin.audioPlayers.get(sBotName.getSingle(e));
            if(player.equals(((EvntAudioPlayerTrackStart)e).getPlayer())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }
}

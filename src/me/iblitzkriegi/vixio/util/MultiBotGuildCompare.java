package me.iblitzkriegi.vixio.util;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.iblitzkriegi.vixio.events.*;
import net.dv8tion.jda.entities.User;
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
        if(e instanceof EvntGuildMessageReceive) {
            User bot = ((EvntGuildMessageReceive) e).getJDA().getSelfInfo();
            if (bot.getUsername().equalsIgnoreCase(sBotName.getSingle(e))) {
                return true;
            }
        }else if(e instanceof EvntPrivateMessageReceive){
            User bot = ((EvntPrivateMessageReceive) e).getJDA().getSelfInfo();
            if (bot.getUsername().equalsIgnoreCase(sBotName.getSingle(e))) {
                return true;
            }
        }else if(e instanceof EvntGuildMemberJoin){
            User bot = ((EvntGuildMemberJoin) e).getEvntJDA().getSelfInfo();
            if (bot.getUsername().equalsIgnoreCase(sBotName.getSingle(e))) {
                return true;
            }
        }else if(e instanceof EvntGuildMemberLeave){
            User bot = ((EvntGuildMemberLeave) e).getEvntJDA().getSelfInfo();
            if (bot.getUsername().equalsIgnoreCase(sBotName.getSingle(e))) {
                return true;
            }
        }else if(e instanceof EvntUserStatusChange){
            User bot = ((EvntUserStatusChange) e).getEvntJDA().getSelfInfo();
            if (bot.getUsername().equalsIgnoreCase(sBotName.getSingle(e))) {
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

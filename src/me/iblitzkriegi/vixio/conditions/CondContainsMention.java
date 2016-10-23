package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/21/2016.
 */
public class CondContainsMention extends Condition {
    @Override
    public boolean check(Event e) {
        boolean doescontain;
        Message message = ((EvntGuildMsgReceived)e).getMsgObject();
        if(message.getMentionedUsers().size() > 0){
            doescontain = true;
        }else{
            doescontain = false;
        }
        return doescontain;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

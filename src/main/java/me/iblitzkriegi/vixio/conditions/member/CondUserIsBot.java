package me.iblitzkriegi.vixio.conditions.member;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.CondAnnotation;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 2/11/2017.
 */
@CondAnnotation.Condition(
        name = "UserIsBot",
        title = "User is bot",
        desc = "Check if a user is a bot account",
        syntax = "user %user% is bot",
        example = "if user event-user is bot")
public class CondUserIsBot extends Condition {
    Expression<User> vUser;
    @Override
    public boolean check(Event e) {
        if(vUser.getSingle(e).isBot()){
            return true;
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vUser = (Expression<User>) expressions[0];
        return true;
    }
}

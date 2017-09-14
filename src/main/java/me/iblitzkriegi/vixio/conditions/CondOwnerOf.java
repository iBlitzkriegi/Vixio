package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 10/30/2016.
 */
@CondAnnotation.Condition(
        name = "IsOwner",
        title = "Is Owner",
        desc = "Check if a user owns a message",
        syntax = "user %string% owns message %message%",
        example = "SUBMIT EXAMPLES TO BLITZ#3273")
public class CondOwnerOf extends Condition {
    private Expression<Message> vMessage;
    private Expression<String> vUser;
    @Override
    public boolean check(Event e) {
        return vMessage.getSingle(e).getAuthor().getId().equalsIgnoreCase(vUser.getSingle(e));

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<Message>) expr[0];
        vUser = (Expression<String>) expr[1];
        return true;
    }
}

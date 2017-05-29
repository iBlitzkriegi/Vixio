package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 5/20/2017.
 */
@CondAnnotation.Condition(
        name = "MessageIsEdited",
        title = "Message is Edited",
        desc = "Check if a message has been Edited",
        syntax = "message %message% is edited",
        example = "SUBMIT EXAMPLES TO BLITZ#3273"
)
public class CondMessageIsEditied extends Condition{
    private Expression<Message> vMessage;
    @Override
    public boolean check(Event e) {
        return vMessage.getSingle(e).isEdited();
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<Message>) expr[0];
        return true;
    }


}

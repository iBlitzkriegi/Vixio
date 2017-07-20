package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.List;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@EffectAnnotation.Effect(
        name = "PurgeMessages",
        title = "Purge Messages",
        desc = "Remove/Clear a certain amount of messages in a channel, must have permission to do so",
        syntax = "(purge|clear) %number% message[']s in [channel with id] %string% with %string%",
        example = "SOON"
)
public class EffPurgeMessages extends Effect {
    private Expression<Number> vPurge;
    private Expression<String> vBot;
    private Expression<String> vChannel;
    @Override
    protected void execute(Event e) {
        try {
            JDA jda = bots.get(vBot.getSingle(e));
            TextChannel channel = jda.getTextChannelById(vChannel.getSingle(e));
            MessageHistory history = channel .getHistory();
            List<Message> messages = history.retrievePast(Integer.parseInt(String.valueOf(vPurge.getSingle(e)))).complete();
            for (Message s : messages) {
                s.delete().queue();
            }
        } catch (PermissionException x){
            x.printStackTrace();
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vPurge = (Expression<Number>) expr[0];
        vChannel = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

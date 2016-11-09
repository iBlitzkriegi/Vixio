package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.MessageHistory;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.List;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/4/2016.
 */
@EffectAnnotation.Effect(syntax = "(purge|clear) %integer% message[']s in [channel] %string% with %string%")
public class EffPurgeMessages extends Effect {
    private Expression<Integer> vPurge;
    private Expression<String> vBot;
    private Expression<String> vChannel;
    @Override
    protected void execute(Event e) {
        try {
            JDA jda = bots.get(vBot.getSingle(e));
            TextChannel channel = jda.getTextChannelById(vChannel.getSingle(e));
            MessageHistory history = channel .getHistory();
            List<Message> messages = history.retrieve(vPurge.getSingle(e));
            for (Message s : messages) {
                s.deleteMessage();
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
        vPurge = (Expression<Integer>) expr[0];
        vBot = (Expression<String>) expr[2];
        vChannel = (Expression<String>) expr[1];
        return true;
    }
}

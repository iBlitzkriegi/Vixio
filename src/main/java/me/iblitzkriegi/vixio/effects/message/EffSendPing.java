package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 10/30/2016.
 */
@EffectAnnotation.Effect(
        name = "SendMessage",
        title = "Send Ping",
        desc = "Send your bots Ping to a channel",
        syntax = "[discord ]send ping to channel [with id] %string% as [bot] %string%",
        example = "SOON"
)
public class EffSendPing extends Effect {
    private Expression<String> sChannel;
    private Expression<String> sBot;
    @Override
    protected void execute(Event e) {
        if(bots.get(sBot.getSingle(e))!=null) {
            JDA jda = bots.get(sBot.getSingle(e));
            long current = System.currentTimeMillis();
            TextChannel chnl = jda.getTextChannelById(sChannel.getSingle(e));
            chnl.sendMessage("Pinging...").queue(reply -> {
                long diff = System.currentTimeMillis() - current;
                reply.editMessage("Pong! " + String.valueOf(diff) + " ms.").queue();
            });

        }else{
            Skript.warning("Could not find bot with the name: " + sBot.getSingle(e));
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sChannel = (Expression<String>) expr[0];
        sBot = (Expression<String>) expr[1];
        return true;
    }
}

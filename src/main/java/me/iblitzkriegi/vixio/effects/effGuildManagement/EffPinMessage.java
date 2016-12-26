package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] pin message %string% in channel %string% with %string%")
public class EffPinMessage extends Effect {
    Expression<String> vMessage;
    Expression<String> vChannel;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        try {
            JDA jda = bots.get(vBot.getSingle(e));
            TextChannel channel = jda.getTextChannelById(vChannel.getSingle(e));
            channel.pinMessageById(vMessage.getSingle(e)).queue();
        }catch (PermissionException x){
            Skript.warning(x.getLocalizedMessage());
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<String>) expr[0];
        vChannel = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

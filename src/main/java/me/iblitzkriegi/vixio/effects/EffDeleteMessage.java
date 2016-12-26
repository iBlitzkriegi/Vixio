package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] delete message %message% with %string%")
public class EffDeleteMessage extends Effect {
    Expression<Message> vMessage;
    Expression<String> vBot;

    @Override
    protected void execute(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Message msg = vMessage.getSingle(e);
        jda.getTextChannelById(msg.getTextChannel().getId()).deleteMessageById(msg.getId()).queue();

    }



    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<Message>) expr[0];
        vBot = (Expression<String>) expr[1];
        return true;
    }
}

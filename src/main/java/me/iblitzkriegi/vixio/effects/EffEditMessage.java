package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(
        name = "EditMessage",
        title = "Edit Message",
        desc = "Edit a Message",
        syntax = "[discord] edit message %message% to say %string% with [bot] %string%",
        example = "SOON"
)
public class EffEditMessage extends Effect {
    Expression<Message> vMessage;
    Expression<String> vBot;
    Expression<String> vNew;

    @Override
    protected void execute(Event e) {
        JDA jda = EffLogin.bots.get(vBot.getSingle(e));
        Message msg = vMessage.getSingle(e);
        jda.getTextChannelById(msg.getTextChannel().getId()).editMessageById(msg.getId(), vNew.getSingle(e)).queue();

    }



    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<Message>) expr[0];
        vNew = (Expression<String>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

package me.iblitzkriegi.vixio.effects.message;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.vdurmont.emoji.EmojiParser;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 2/5/2017.
 */
@EffectAnnotation.Effect(
        name = "AddReaction",
        title = "Add Reaction to a Message",
        desc = "Add a Reaction to a Message",
        syntax = "discord add reaction %string% to message %message% with bot %string%",
        example = "SOON"
)
public class EffAddReaction extends Effect{
    Expression<String> vReaction;
    Expression<Message> vMessage;
    Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        if(EffLogin.bots.get(vBot.getSingle(e))!=null){
            JDA jda = EffLogin.bots.get(vBot.getSingle(e));
            String result = EmojiParser.parseToUnicode(vReaction.getSingle(e));
            jda.getTextChannelById(vMessage.getSingle(e).getTextChannel().getId()).getMessageById(vMessage.getSingle(e).getId()).queue(message -> message.addReaction(result).queue());

        }else{
            System.out.println("Could not find bot by that name!");
        }

    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vReaction = (Expression<String>) expr[0];
        vMessage = (Expression<Message>) expr[1];
        vBot = (Expression<String>) expr[2];
        return true;
    }
}

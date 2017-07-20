package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.annotation.ExprAnnotation;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;

import java.util.List;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/20/2016.
 */
@ExprAnnotation.Expression(
        name = "TextChannelsOfBot",
        title = "Text Channels of Bot",
        desc = "Get all the Text-Channel's your bot is in",
        syntax = "[discord] %string%('s|s) textchannel[s]",
        returntype = List.class,
        type = ExpressionType.SIMPLE,
        example = "SUBMIT EXAMPLES TO Blitz#3273"
)
public class ExprTextChannels extends SimpleExpression<List> {
    Expression<String> vBot;
    @Override
    protected List[] get(Event e) {
        return new List[]{getTc(e)};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends List> getReturnType() {
        return List.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) e[0];
        return true;
    }
    private List getTc(Event e){
        JDA jda = bots.get(vBot.getSingle(e));
        return jda.getTextChannels();
    }
}

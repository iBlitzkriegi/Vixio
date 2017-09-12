package me.iblitzkriegi.vixio.conditions.bot;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.vCondShowroom;

/**
 * Created by Blitz on 11/19/2016.
 */
public class CondBotIsLoggedIn extends Condition{
    static {
        Documentation docs = Vixio.registerCondition(CondBotIsLoggedIn.class, "bot %string% is logged in", "bot %string% is not logged in")
                .setName("Bot is logged in")
                .setDesc("Check if the bot is, or is not, logged in")
                .setExample("COMING SOON");
        String t = docs.getName().replaceAll(" ", "");
        vCondShowroom.put(t, docs.getName());
        VixioAnnotationParser.vCondSyntax.put(docs.getName().replaceAll(" ", ""), docs.getSyntaxes()[0]);
        VixioAnnotationParser.vCondExample.put(docs.getName().replaceAll(" ", ""), docs.getExample());
        VixioAnnotationParser.vCondDesc.put(docs.getName().replaceAll(" ", ""), docs.getDesc());
    }
    Expression<String> vBot;
    private boolean not;
    @Override
    public boolean check(Event e) {
        Boolean t = EffLogin.bots.get(vBot.getSingle(e))!=null ? true : false;
        return (not == t);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        not = i == 0;

        return true;
    }
}

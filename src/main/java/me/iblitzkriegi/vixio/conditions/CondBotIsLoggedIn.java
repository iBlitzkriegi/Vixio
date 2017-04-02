package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/19/2016.
 */
@CondAnnotation.Condition(
        name = "BotIsLoggedIn",
        title = "Bot is logged In",
        desc = "Check if one of your bots is logged in",
        syntax = "bot %string% is logged in",
        example = "command /islogged:\\n" +
                "\\ttrigger:\\n" +
                "\\t\\tif bot \\\"Rawr\\\" is logged in:\\n" +
                "\\t\\t\\tbroadcast \\\"Bot's logged in!\\\"")
public class CondBotIsLoggedIn extends Condition{
    Expression<String> vBot;
    @Override
    public boolean check(Event e) {
        if(EffLogin.bots.get(vBot.getSingle(e))!=null){
            return true;
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        return true;
    }
}

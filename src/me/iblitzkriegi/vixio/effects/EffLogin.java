package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.jdaEvents.GuildMessage;
import me.iblitzkriegi.vixio.jdaEvents.PrivateMessage;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.utils.SimpleLog;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;

/**
 * Created by Blitz on 10/14/2016.
 */
public class EffLogin extends Effect {
    private Expression<String> token;
    public static JDA s;
    @Override
    protected void execute(Event e) {
        if(s==null) {
            try {
                SimpleLog.LEVEL = SimpleLog.Level.OFF;
                JDA api = new JDABuilder().setBotToken(token.getSingle(e)).setBulkDeleteSplittingEnabled(false).addListener(new GuildMessage()).addListener(new PrivateMessage()).buildBlocking();
                s = api;
            } catch (LoginException x) {
                x.printStackTrace();
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }else if(s.getStatus() == JDA.Status.DISCONNECTED){
            SimpleLog.LEVEL = SimpleLog.Level.OFF;
            JDA api = null;
            try {
                api = new JDABuilder().setBotToken(token.getSingle(e)).setBulkDeleteSplittingEnabled(false).addListener(new GuildMessage()).addListener(new PrivateMessage()).buildBlocking();
            } catch (LoginException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            s = api;

        }else{
            Skript.warning("You may only login to one bot at a time currently.");
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        token = (Expression<String>) expressions[0];
        return true;
    }
}

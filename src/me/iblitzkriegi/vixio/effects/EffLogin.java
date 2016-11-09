package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.jdaEvents.*;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.player.MusicPlayer;
import net.dv8tion.jda.utils.SimpleLog;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

/**
 * Created by Blitz on 10/30/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord ]login to user with token %string% with name %string%")
public class EffLogin extends Effect {
    public static HashMap<String, JDA> bots = new HashMap<>();
    public static HashMap<String, Long> botruntime = new HashMap<>();
    public static HashMap<String, MusicPlayer> musicPlayerHashMap = new HashMap<>();
    Expression<String> token;
    Expression<String> name;
    @Override
    protected void execute(Event e) {
        if(bots.get(name.getSingle(e)) == null) {
            try {
                SimpleLog.LEVEL = SimpleLog.Level.OFF;
                JDA api = new JDABuilder().setBotToken(token.getSingle(e))
                        .addListener(new GuildMessageReceived())
                        .addListener(new PrivateMessageReceived())
                        .addListener(new GuildMemberJoin())
                        .addListener(new GuildMemberLeave())
                        .addListener(new UserUpdateStatus())
                        .buildBlocking();
                bots.put(name.getSingle(e), api);
                java.util.Date date = new java.util.Date();
                botruntime.put(name.getSingle(e), date.getTime());
                MusicPlayer player = new MusicPlayer();
                musicPlayerHashMap.put( name.getSingle(e),player);
            } catch (LoginException x) {
                x.printStackTrace();
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }
    }



    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        token = (Expression<String>) expr[0];
        name = (Expression<String>) expr[1];
        return true;
    }
}

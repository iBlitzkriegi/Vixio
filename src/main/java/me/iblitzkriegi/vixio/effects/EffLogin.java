package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.effects.EffLog;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.jdaEvents.*;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.managers.AudioManager;
import net.dv8tion.jda.core.managers.impl.AudioManagerImpl;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Blitz on 10/30/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord ]login to user with token %string% with name %string%")
public class EffLogin extends Effect {
    public static HashMap<String, JDA> bots = new HashMap<>();
    public static HashMap<String, Long> botruntime = new HashMap<>();
    public static HashMap<String, User> users = new HashMap<>();
    public static HashMap<String, AudioPlayerManager> audioManagers = new HashMap<>();
    public static HashMap<String, AudioPlayer> audioPlayers = new HashMap<>();
    public static HashMap<String, TrackScheduler> trackSchedulers = new HashMap<>();
    Expression<String> token;
    Expression<String> name;
    @Override
    protected void execute(Event e) {
        if(bots.get(name.getSingle(e)) == null) {
            try {
                SimpleLog.LEVEL = SimpleLog.Level.OFF;
                JDA api = new JDABuilder(AccountType.BOT).setToken(token.getSingle(e))
                        .addListener(new GuildMessageReceived())
                        .addListener(new PrivateMessageReceived())
                        .addListener(new GuildMemberJoin())
                        .addListener(new GuildMemberLeave())
                        .addListener(new UserUpdateStatus())
                        .buildBlocking();
                // Audio hashmaps \\
                AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                AudioSourceManagers.registerRemoteSources(playerManager);
                audioManagers.put(name.getSingle(e), playerManager);
                AudioPlayer player = playerManager.createPlayer();
                TrackScheduler trackScheduler = new TrackScheduler(player);
                player.addListener(trackScheduler);
                trackSchedulers.put(name.getSingle(e), trackScheduler);
                audioPlayers.put(name.getSingle(e), player);
                bots.put(name.getSingle(e), api);
                users.put(name.getSingle(e), api.getSelfUser());
                java.util.Date date = new java.util.Date();

                botruntime.put(name.getSingle(e), date.getTime());

            } catch (LoginException x) {
                x.printStackTrace();
            } catch (InterruptedException x) {
                x.printStackTrace();
            } catch (RateLimitedException e1) {
                e1.printStackTrace();
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

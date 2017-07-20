package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.jda.guild.GuildBan;
import me.iblitzkriegi.vixio.jda.guild.GuildMessageReceived;
import me.iblitzkriegi.vixio.jda.guild.TextChannelCreated;
import me.iblitzkriegi.vixio.jda.guild.TextChannelDeleted;
import me.iblitzkriegi.vixio.jda.member.*;
import me.iblitzkriegi.vixio.jda.message.BotSendGuildMessage;
import me.iblitzkriegi.vixio.jda.message.MessageAddReaction;
import me.iblitzkriegi.vixio.jda.message.PrivateMessageReceived;
import me.iblitzkriegi.vixio.jda.message.PrivateMessageSent;
import me.iblitzkriegi.vixio.registration.annotation.EffectAnnotation;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.AccountTypeException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Blitz on 10/30/2016.
 */
@EffectAnnotation.Effect(
        name = "LoginToBot",
        title = "Connect To Bot",
        desc = "Connect to a Bot via a Token, Tutorial on Vixio YT channel",
        syntax = "[discord ]login to user with token %string% with name %string%",
        example = "SOON"
)
public class EffLogin extends Effect {
    public static HashMap<String, JDA> bots = new HashMap<>();
    public static HashMap<String, Long> botruntime = new HashMap<>();
    public static HashMap<String, AudioPlayerManager> audioManagers = new HashMap<>();
    public static HashMap<AudioPlayer, User> audioPlayers = new HashMap<>();
    public static HashMap<String, TrackScheduler> trackSchedulers = new HashMap<>();
    public static HashMap<String, User> users = new HashMap<>();

    Expression<String> token;
    Expression<String> name;
    private static JDA api;
    @Override
    protected void execute(Event e) {

        Bukkit.getScheduler().runTaskAsynchronously(Vixio.getPl(), () -> {
        if (bots.get(name.getSingle(e)) == null) {
            try {
                SimpleLog.LEVEL = SimpleLog.Level.OFF;
                try {
                    api = new JDABuilder(AccountType.BOT).setToken(token.getSingle(e))
                            .addEventListener(new GuildMessageReceived())
                            .addEventListener(new PrivateMessageReceived())
                            .addEventListener(new GuildMemberJoin())
                            .addEventListener(new GuildMemberLeave())
                            .addEventListener(new UserUpdateStatus())
                            .addEventListener(new UserJoinVc())
                            .addEventListener(new UserLeaveVc())
                            .addEventListener(new TextChannelCreated())
                            .addEventListener(new TextChannelDeleted())
                            .addEventListener(new UserAvatarChange())
                            .addEventListener(new GuildBan())
                            .addEventListener(new BotSendGuildMessage())
                            .addEventListener(new UserStartStreaming())
                            .addEventListener(new MessageAddReaction())
                            .addEventListener(new PrivateMessageSent())
                            .buildBlocking();
                }catch (AccountTypeException x){
                    api = new JDABuilder(AccountType.CLIENT).setToken(token.getSingle(e))
                            .addEventListener(new GuildMessageReceived())
                            .addEventListener(new PrivateMessageReceived())
                            .addEventListener(new GuildMemberJoin())
                            .addEventListener(new GuildMemberLeave())
                            .addEventListener(new UserUpdateStatus())
                            .addEventListener(new UserJoinVc())
                            .addEventListener(new UserLeaveVc())
                            .addEventListener(new TextChannelCreated())
                            .addEventListener(new TextChannelDeleted())
                            .addEventListener(new UserAvatarChange())
                            .addEventListener(new GuildBan())
                            .addEventListener(new BotSendGuildMessage())
                            .addEventListener(new UserStartStreaming())
                            .addEventListener(new MessageAddReaction())
                            .addEventListener(new PrivateMessageSent())
                            .buildBlocking();
                }

                bots.put(name.getSingle(e), api);
                users.put(name.getSingle(e), api.getSelfUser());
                java.util.Date date = new java.util.Date();
                botruntime.put(name.getSingle(e), date.getTime());
                if(getNext()!=null) {
                    TriggerItem.walk(getNext(), e);
                }
            } catch (LoginException x) {
                x.printStackTrace();
            } catch (RateLimitedException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } else {
            Skript.warning("There is already a bot logged in with that name!");
        }

        });
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

    @Override
    protected TriggerItem walk(Event e) {
        delay(e);
        execute(e);
        return null;
    }

    private static final Field DELAYED;
    static {
        Field _DELAYED = null;
        try {
            _DELAYED = Delay.class.getDeclaredField("delayed");
            _DELAYED.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Skript.warning("Skript's 'delayed' method could not be resolved. Some Skript warnings may " +
                    "not be available.");
        }
        DELAYED = _DELAYED;
    }
    private void delay(Event e) {
        if (DELAYED != null) {
            try {
                ((Set<Event>) DELAYED.get(null)).add(e);
            } catch (IllegalAccessException ignored) {
            }
        }
    }

}

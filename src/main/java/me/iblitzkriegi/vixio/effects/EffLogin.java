package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.jda.JDAEventListener;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.AccountTypeException;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by Blitz on 7/22/2017.
 */
public class EffLogin extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffLogin.class, "(login|connect) to discord account with token %string% named %string%")
                .setName("Connect effect")
                .setDesc("Login to a bot account with a token")
                .setExample("login to discord account with token \"MjM3MDYyNzE0MTY0MjQ4NTc2.DFfAvg.S_YgY26hqyS1SgNvibrpcdhSk94\" named \"Rawr\"");
    }
    private Expression<String> token;
    private Expression<String> name;
    @Override
    protected void execute(Event e) {
        String token = this.token.getSingle(e);
        String name = this.name.getSingle(e);
        if (name == null || token == null || token.isEmpty()) {
            return;
        }
        if (Vixio.getInstance().botNameHashMap.get(name) != null) {
            Vixio.getErrorHandler().warn("Vixio attempted to login to a bot with the name " + name + " but a bot already exists with that name.");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Vixio.getAddonInstance().plugin, () -> {
            JDA api = null;
            try {
                api = new JDABuilder(AccountType.BOT).setToken(token)
                        .addEventListener(new JDAEventListener())
                        .buildBlocking();
            } catch (LoginException | InterruptedException e1) {
                e1.printStackTrace();
            } catch (AccountTypeException x) {
                try {
                    api = new JDABuilder(AccountType.CLIENT).setToken(token)
                            .addEventListener(new JDAEventListener())
                            .buildBlocking();
                } catch (LoginException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                Bot bot = new Bot(name, api);
                Vixio.getInstance().botHashMap.put(api, bot);
                Vixio.getInstance().botNameHashMap.put(name, bot);
            } catch (NullPointerException x) {

            }
            if (getNext() != null) {
                TriggerItem.walk(getNext(), e);
            }
        });

    }

    @Override
    public String toString(Event event, boolean b) {
        return "login to discord account with token " + token.toString(event, b) + " named " + name.toString(event, b);
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

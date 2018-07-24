package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.CommandListener;
import me.iblitzkriegi.vixio.events.base.EventListener;
import me.iblitzkriegi.vixio.util.MessageUpdater;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.AccountTypeException;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;
import java.time.Instant;

public class EffLogin extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffLogin.class, "(login|connect) to %string% (using|with) [the] name %string%")
                .setName("Login")
                .setDesc("Login to a bot account with a token")
                .setExample(
                        "on skript load:",
                        "\tlogin to \"MjM3MDYyNzE0MTY0MjQ4NTc2.DFfAvg.S_YgY26hqyS1SgNvibrpcdhSk94\" with the name \"VixioButler\""
                );
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
            // just to make the error show up outside of skript's reload errors
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Vixio.getInstance(),
                    () -> Vixio.getErrorHandler().warn("Vixio attempted to login to a bot with the name " + name + " but a bot already exists with that name."),
                    1);
            return;
        }

        JDA api = null;
        try {
            try {
                api = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
            } catch (AccountTypeException x) {
                api = new JDABuilder(AccountType.CLIENT).setToken(token).buildBlocking();
            }
        } catch (LoginException | InterruptedException e1) {
            Vixio.getErrorHandler().warn("Vixio tried to login but encountered \"" + e1.getMessage() + "\"");
            Vixio.getErrorHandler().warn("Maybe your token is wrong?");
            return;
        }

        // Make the new bot listen to active events and commands
        for (EventListener<?> listener : EventListener.listeners) {
            api.addEventListener(listener);
        }

        api.addEventListener(new CommandListener());
        api.addEventListener(new MessageUpdater());
        Bot bot = new Bot(name, api);

        bot.setLoginTime(Instant.now().getEpochSecond());
        Vixio.getInstance().botHashMap.put(api, bot);
        Vixio.getInstance().botNameHashMap.put(name, bot);
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

}

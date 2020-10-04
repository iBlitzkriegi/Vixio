package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.commands.CommandListener;
import me.iblitzkriegi.vixio.events.base.EventListener;
import me.iblitzkriegi.vixio.scopes.ScopeMakeBot;
import me.iblitzkriegi.vixio.util.MessageUpdater;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.AccountTypeException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import javax.security.auth.login.LoginException;
import java.time.Instant;

public class EffLogin extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffLogin.class, "(login|connect) to %string% (using|with) [the] name %string%")
                .setName("Login")
                .setDesc("This is for verified bots or bots that have enabled privileged intents and need to activate them in Vixio. The intents must be enabled before you login to your bot.")
                .setExample(
                        "on skript load:",
                        "\tcreate vixio bot:",
                        "\t\tenable the guild members intent",
                        "\t\tlogin to \"YAHITAMUH\" with the name \"Neeko\""
                );
    }

    private Expression<String> token;
    private Expression<String> name;
    private boolean scope = false;

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

        JDABuilder api = scope ? ScopeMakeBot.jdaBuilder.setToken(token) : JDABuilder.createDefault(token);

        // Make the new bot listen to active events and commands

        for (EventListener<?> listener : EventListener.listeners) {
            api.addEventListeners(listener);
        }
        api.addEventListeners(
                new CommandListener(),
                new MessageUpdater()
        );
        JDA jda;
        try {
            jda = api.build().awaitReady();
        } catch (LoginException | InterruptedException e1) {
            Vixio.getErrorHandler().warn("Vixio tried to login but encountered \"" + e1.getMessage() + "\"");
            Vixio.getErrorHandler().warn("Maybe your token is wrong?");
            return;
        }
        Bot bot = new Bot(name, jda);

        bot.setLoginTime(Instant.now().getEpochSecond());
        Vixio.getInstance().botHashMap.put(jda, bot);
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
        scope = EffectSection.isCurrentSection(ScopeMakeBot.class);
        return true;
    }

}

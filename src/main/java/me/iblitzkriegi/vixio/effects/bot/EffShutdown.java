package me.iblitzkriegi.vixio.effects.bot;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import org.bukkit.event.Event;

public class EffShutdown extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffShutdown.class, "(logout [of]|shutdown) %bot/string%")
                .setName("Logout of Bot")
                .setDesc("Shutdown or logout of a bot, destroys the instance and closes the connection")
                .setExample(
                        "discord command $shutdown:",
                        "\ttrigger:",
                        "\t\tshutdown event-bot"
                );
    }

    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return;
        }

        bot.getShardMananger().shutdown();
        Vixio.getInstance().botHashMap.remove(bot);
        Vixio.getInstance().botNameHashMap.remove(bot.getName());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "shutdown " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        return true;
    }
}

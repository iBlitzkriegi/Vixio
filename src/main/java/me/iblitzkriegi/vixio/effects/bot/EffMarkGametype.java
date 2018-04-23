package me.iblitzkriegi.vixio.effects.bot;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Game;
import org.bukkit.event.Event;

public class EffMarkGametype extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffMarkGametype.class, "mark %bot/string% as %gametype% [to] [with title] %string% [(and|on|with|at) url %-string%]")
                .setName("Mark Bot as Gametype")
                .setDesc("Set a bot's game to a certain gametype. This can be: streaming/watching/playing/listening. For streaming you must include a valid twitch URL to stream to and a title.")
                .setExample("mark event-bot as streaming with title \"Wow!\" and url \"https://www.twitch.tv/blitz_xox\"");
    }

    private Expression<Object> bot;
    private Expression<Game.GameType> gameType;
    private Expression<String> title;
    private Expression<String> url;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Game.GameType gameType = this.gameType.getSingle(e);
        String title = this.title.getSingle(e);
        if (bot == null || gameType == null || title == null) {
            return;
        }
        switch (gameType) {
            case DEFAULT:
            case WATCHING:
            case LISTENING:
                bot.getJDA().getPresence().setGame(Game.of(gameType, title));
                break;
            case STREAMING:
                if (url == null) {
                    return;
                }
                String url = this.url.getSingle(e);
                if (!Game.isValidStreamingUrl(url)) {
                    return;
                }
                bot.getJDA().getPresence().setGame(Game.of(gameType, title, url));

        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "mark " + bot.toString(e, debug) + " as " + gameType.toString(e, debug) + " with title " + title.toString() + (url == null ? "" : " with url " + url.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        gameType = (Expression<Game.GameType>) exprs[1];
        title = (Expression<String>) exprs[2];
        url = (Expression<String>) exprs[3];
        return true;
    }
}

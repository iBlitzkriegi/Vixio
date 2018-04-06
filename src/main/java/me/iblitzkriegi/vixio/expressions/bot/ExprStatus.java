package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.OnlineStatus;
import org.bukkit.event.Event;

public class ExprStatus extends SimpleExpression<OnlineStatus> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprStatus.class, OnlineStatus.class,
                "[online][(-| )] status", "bot/string")
                .setName("Online status of bot")
                .setDesc("Get the online status of a bot. This may be set.")
                .setExample("set the online status of \"Jewel\" to do not disturb");
    }

    private Expression<Object> bot;

    @Override
    protected OnlineStatus[] get(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null) {
            return null;
        }

        return new OnlineStatus[]{bot.getJDA().getPresence().getStatus()};
    }


    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends OnlineStatus> getReturnType() {
        return OnlineStatus.class;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "online status of " + bot.toString(e, debug);
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{OnlineStatus.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot != null) {
            bot.getJDA().getPresence().setStatus((OnlineStatus) delta[0]);
        }
    }
}

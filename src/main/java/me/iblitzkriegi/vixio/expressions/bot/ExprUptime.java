package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import ch.njol.skript.util.Timespan;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;

import java.time.Instant;

public class ExprUptime extends SimplePropertyExpression<Object, Timespan> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprUptime.class, Date.class, "uptime", "strings/bots")
                .setName("Uptime Of Bot")
                .setDesc("Get the amount of time a bot has been up in a skript date form.")
                .setExample(
                        "discord command $uptime:",
                        "\ttrigger:",
                        "\t\treply with \"I've been awake for %uptime of event-bot%\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "uptime";
    }

    @Override
    public Timespan convert(Object o) {
        Bot bot = Util.botFrom(o);
        if (bot != null) {
            return Timespan.fromTicks_i((Instant.now().getEpochSecond() - bot.getUptime()) * 20);
        }
        return null;
    }

    @Override
    public Class<? extends Timespan> getReturnType() {
        return Timespan.class;
    }
}

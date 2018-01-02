package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

import java.time.Instant;

public class ExprTimestampOfEmbed extends SimplePropertyExpression<EmbedBuilder, Date> {


    static {
        Vixio.getInstance().registerPropertyExpression(ExprTimestampOfEmbed.class, Date.class,
                "(timestamp|date)[s]", "embedbuilders", "[embed[s]]")
                .setName("Timestamp of Embed")
                .setDesc("Returns the timestamp of an embed. Can be set to any date (e.g. now).")
                .setExample("set the timestamp of {_embed} to now",
                        "",
                        "# You can also set it to a date in the past/future like this",
                        "",
                        "set {_thePast} to now",
                        "subtract 5 days from {_thePast}",
                        "set the timestamp of {_embed} to {_thePast}"
                );
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public Date convert(final EmbedBuilder embed) {
        return embed.isEmpty() || embed.build().getTimestamp() == null ? null :
                new Date(embed.build().getTimestamp().toInstant().getEpochSecond() * 1000);
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if ((mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    Date.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setTimestamp(null);
                return;

            case SET:
                embed.setTimestamp(Instant.ofEpochMilli(((Date) delta[0]).getTimestamp()));
        }
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    protected String getPropertyName() {
        return "timestamp of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the timestamp of " + getExpr().toString(e, debug);
    }

}

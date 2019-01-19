package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

import java.time.Instant;

public class ExprTimestamp extends SimplePropertyExpression<Object, Date> {


    static {
        Vixio.getInstance().registerPropertyExpression(ExprTimestamp.class, Date.class,
                "(timestamp|date)", "embedbuilders/messages")
                .setName("Timestamp of")
                .setDesc("Returns the timestamp of either a message or an embed. You can set the time of an embed to any date (e.g. now).")
                .setExample(
                        "set the timestamp of {_embed} to now",
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
        setExpr(exprs[0]);
        return true;
    }

    @Override
    public Date convert(final Object object) {
        if (object instanceof EmbedBuilder) {
            EmbedBuilder embed = (EmbedBuilder) object;
            return embed.isEmpty() || embed.build().getTimestamp() == null ? null : Util.getDate(embed.build().getTimestamp());
        } else if (object instanceof UpdatingMessage) {
            Message message = ((UpdatingMessage) object).getMessage();
            return Util.getDate(message.getCreationTime());
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if ((mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    Date.class
            };
        }
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final ChangeMode mode) {
        Object input = getExpr().getSingle(e);
        if (!(input instanceof EmbedBuilder)) {
            return;
        }
        EmbedBuilder embed = (EmbedBuilder) input;
        if (embed == null) {
            return;
        }

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
        return "timestamp of";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the timestamp of " + getExpr().toString(e, debug);
    }

}

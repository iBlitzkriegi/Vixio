package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.event.Event;

public class ExprDescriptionOfEmbed extends SimplePropertyExpression<EmbedBuilder, String> {


    static {
        Vixio.getInstance().registerPropertyExpression(ExprDescriptionOfEmbed.class, String.class,
                "description[s]", "embedbuilders")
                .setName("Description of Embed")
                .setDesc("Returns the description of an embed. Can be set to any string (e.g. \"Hi there!\").");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public String convert(final EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getDescription();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if ((mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    String.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final ChangeMode mode) {

        if (delta == null && (mode != ChangeMode.DELETE && mode != ChangeMode.RESET)) return;

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setDescription(null);
                return;

            case SET:
                embed.setDescription((String) delta[0]);
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "description of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the description of " + getExpr().toString(e, debug);
    }

}

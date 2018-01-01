package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprFooterOfEmbed extends SimplePropertyExpression<EmbedBuilder, MessageEmbed.Footer> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprFooterOfEmbed.class, MessageEmbed.Footer.class,
                "footer[s] [<icons?>]", "embedbuilders", "[embed[s]]")
                .setName("Footer of Embed")
                .setDesc("Returns the footer of an embed. Can be set any footer.")
                .setExample("set footer of {_embed} to a footer with text \"Hi Pika\" and icon \"https://i.imgur.com/TQgR2hW.jpg\"");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public MessageEmbed.Footer convert(final EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getFooter();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    MessageEmbed.Footer.class
            };
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {

        if (delta == null && (mode != Changer.ChangeMode.DELETE && mode != Changer.ChangeMode.RESET)) return;

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setFooter(null, null);
                return;

            case SET:
                MessageEmbed.Footer footer = (MessageEmbed.Footer) delta[0];
                embed.setFooter(footer.getText(), footer.getIconUrl());

        }
    }

    @Override
    public Class<? extends MessageEmbed.Footer> getReturnType() {
        return MessageEmbed.Footer.class;
    }

    @Override
    protected String getPropertyName() {
        return "footer of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the footer of " + getExpr().toString(e, debug);
    }

}

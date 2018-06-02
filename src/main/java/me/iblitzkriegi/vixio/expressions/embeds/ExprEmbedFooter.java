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

public class ExprEmbedFooter extends SimplePropertyExpression<EmbedBuilder, MessageEmbed.Footer> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmbedFooter.class, MessageEmbed.Footer.class,
                "footer", "embedbuilders")
                .setName("Footer of Embed")
                .setDesc("Returns the footer of an embed. Can be set any footer.")
                .setExample("set footer of {_embed} to a footer with text \"Hi Pika\" and icon \"https://i.imgur.com/TQgR2hW.jpg\"");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public MessageEmbed.Footer convert(EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getFooter();
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    String.class,
                    MessageEmbed.Footer.class
            };
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {

        EmbedBuilder embed = getExpr().getSingle(e);
        if (embed == null) return;

        switch (mode) {

            case RESET:
            case DELETE:
                embed.setFooter(null, null);
                return;

            case SET:
                MessageEmbed.Footer footer = delta[0] instanceof String ?
                        new EmbedBuilder().setFooter((String) delta[0], null).build().getFooter()
                        : (MessageEmbed.Footer) delta[0];
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
    public String toString(Event e, boolean debug) {
        return "the footer of " + getExpr().toString(e, debug);
    }

}

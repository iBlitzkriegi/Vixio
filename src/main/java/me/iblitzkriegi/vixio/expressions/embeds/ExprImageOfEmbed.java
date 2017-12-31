package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

public class ExprImageOfEmbed extends SimplePropertyExpression<EmbedBuilder, MessageEmbed.ImageInfo> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprImageOfEmbed.class, MessageEmbed.ImageInfo.class,
                "image[s]", "embedbuilders", "embed[s]")
                .setName("Image of Embed")
                .setDesc("Returns the image of an embed. Can be set any url (e.g. \"https://i.imgur.com/TQgR2hW.jpg\").");
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr((Expression<EmbedBuilder>) exprs[0]);
        return true;
    }

    @Override
    public MessageEmbed.ImageInfo convert(final EmbedBuilder embed) {
        return embed.isEmpty() ? null : embed.build().getImage();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) && getExpr().isSingle()) {
            return new Class[]{
                    String.class
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
                embed.setImage(null);
                return;

            case SET:
                String url = (String) delta[0];
                try {
                    embed.setImage(url);
                } catch (IllegalArgumentException e1) {
                    Skript.error("Vixio encountered the error \"" + e1.getMessage() + "\" while trying to set the image of " + getExpr().toString(e, false) + " to " + "\"" + url + "\"");
                }
        }
    }

    @Override
    public Class<? extends MessageEmbed.ImageInfo> getReturnType() {
        return MessageEmbed.ImageInfo.class;
    }

    @Override
    protected String getPropertyName() {
        return "image of embed";
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the image of " + getExpr().toString(e, debug);
    }

}
